package com.ruoyi.framework.config;

import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.utils.CacheUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * 缓存预热组件
 * 用于应用启动后恢复持久化的缓存数据
 * 
 * @author ruoyi
 */
@Component
@ConditionalOnProperty(prefix = "spring.cache", name = { "type" }, havingValue = "jcache", matchIfMissing = false)
public class CacheWarmupRunner implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(CacheWarmupRunner.class);

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("开始执行缓存预热...");

        try {
            // 获取login_tokens缓存
            Cache cache = CacheUtils.getCache("login_tokens");
            if (cache != null) {
                // 尝试获取所有的缓存键，添加重试机制
                Set<String> keys = null;
                int retryCount = 0;
                int maxRetries = 3;

                while (keys == null && retryCount < maxRetries) {
                    try {
                        keys = CacheUtils.getkeys("login_tokens");
                        break;
                    } catch (Exception e) {
                        retryCount++;
                        log.warn("获取缓存键失败，第{}次重试: {}", retryCount, e.getMessage());
                        if (retryCount < maxRetries) {
                            Thread.sleep(100); // 等待100ms后重试
                        }
                    }
                }
                if (keys != null && !keys.isEmpty()) {
                    log.info("发现持久化的登录令牌数量: {}", keys.size());

                    // 验证缓存数据的有效性
                    int validCount = 0;
                    int expiredCount = 0;

                    for (String key : keys) {
                        try {
                            LoginUser loginUser = CacheUtils.get("login_tokens", key, LoginUser.class);
                            if (loginUser != null) {
                                // 检查是否过期
                                long currentTime = System.currentTimeMillis();
                                if (loginUser.getExpireTime() != null && loginUser.getExpireTime() > currentTime) {
                                    validCount++;
                                    log.debug("恢复有效的登录令牌: {}", key);
                                } else {
                                    expiredCount++;
                                    // 清理过期的缓存
                                    CacheUtils.remove("login_tokens", key);
                                    log.debug("清理过期的登录令牌: {}", key);
                                }
                            }
                        } catch (Exception e) {
                            log.warn("处理缓存键 {} 时发生异常: {}", key, e.getMessage());
                        }
                    }

                    log.info("缓存预热完成 - 有效令牌: {}, 过期令牌: {}", validCount, expiredCount);
                } else {
                    log.info("没有发现需要恢复的登录令牌");
                }
            } else {
                log.warn("无法获取login_tokens缓存实例");
            }

            // 预热其他永久缓存
            warmupCache("sys_config", "系统配置");
            warmupCache("sys_dict", "数据字典");

        } catch (Exception e) {
            log.error("缓存预热过程中发生异常", e);
        }
    }

    /**
     * 预热指定缓存
     */
    private void warmupCache(String cacheName, String cacheDesc) {
        try {
            Cache cache = CacheUtils.getCache(cacheName);
            if (cache != null) {
                Set<String> keys = null;
                int retryCount = 0;
                int maxRetries = 3;

                while (keys == null && retryCount < maxRetries) {
                    try {
                        keys = CacheUtils.getkeys(cacheName);
                        break;
                    } catch (Exception e) {
                        retryCount++;
                        log.warn("预热{}缓存获取键失败，第{}次重试: {}", cacheDesc, retryCount, e.getMessage());
                        if (retryCount < maxRetries) {
                            try {
                                Thread.sleep(50); // 等待50ms后重试
                            } catch (InterruptedException ie) {
                                Thread.currentThread().interrupt();
                                return;
                            }
                        }
                    }
                }

                if (keys != null && !keys.isEmpty()) {
                    log.info("{}缓存预热: 发现 {} 条记录", cacheDesc, keys.size());
                } else if (retryCount >= maxRetries) {
                    log.warn("{}缓存预热失败: 重试{}次后仍无法获取缓存键", cacheDesc, maxRetries);
                }
            }
        } catch (Exception e) {
            log.warn("预热{}缓存时发生异常: {}", cacheDesc, e.getMessage());
        }
    }
}
