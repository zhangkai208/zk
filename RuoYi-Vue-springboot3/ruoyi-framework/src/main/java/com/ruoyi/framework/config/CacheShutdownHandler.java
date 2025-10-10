package com.ruoyi.framework.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.CacheManager;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

import jakarta.annotation.PreDestroy;

/**
 * 缓存管理器优雅关闭处理器
 * 确保应用关闭时正确保存持久化缓存数据
 * 
 * @author ruoyi
 */
@Component
@ConditionalOnProperty(prefix = "spring.cache", name = {"type"}, havingValue = "jcache", matchIfMissing = false)
public class CacheShutdownHandler implements ApplicationListener<ContextClosedEvent> {
    
    private static final Logger log = LoggerFactory.getLogger(CacheShutdownHandler.class);
    
    @Autowired
    private CacheManager cacheManager;
    
    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        shutdownCache();
    }
    
    @PreDestroy
    public void preDestroy() {
        shutdownCache();
    }
    
    private void shutdownCache() {
        try {
            if (cacheManager instanceof JCacheCacheManager) {
                JCacheCacheManager jCacheCacheManager = (JCacheCacheManager) cacheManager;
                javax.cache.CacheManager nativeCacheManager = jCacheCacheManager.getCacheManager();
                
                if (nativeCacheManager != null) {
                    // 确保所有缓存数据都已刷新到磁盘
                    Iterable<String> cacheNames = nativeCacheManager.getCacheNames();
                    for (String cacheName : cacheNames) {
                        javax.cache.Cache<Object, Object> cache = nativeCacheManager.getCache(cacheName);
                        if (cache != null) {
                            // 关闭缓存前触发持久化
                            cache.close();
                        }
                    }
                    
                    // 关闭缓存管理器
                    if (!nativeCacheManager.isClosed()) {
                        nativeCacheManager.close();
                    }
                }
            }
        } catch (Exception e) {
            log.error("关闭缓存管理器时发生异常", e);
        }
    }
}
