package com.zk.controller;

import com.zk.pojo.Result;
import com.zk.service.CacheService;
import com.zk.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Redis管理控制器
 */
@RestController
@RequestMapping("/redis")
public class RedisController {

    @Autowired
    private RedisUtil redisUtil;
    
    @Autowired
    private CacheService cacheService;
    
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 获取Redis状态信息
     */
    @GetMapping("/status")
    public Result<Map<String, Object>> getRedisStatus() {
        Map<String, Object> status = new HashMap<>();
        
        // 测试Redis连接
        boolean isConnected = redisUtil.hasKey("test_connection");
        status.put("connected", isConnected);
        
        // 获取一些基本统计信息
        if (isConnected) {
            // 这里可以添加更多Redis统计信息
            status.put("message", "Redis连接正常");
        } else {
            status.put("message", "Redis连接异常");
        }
        
        return Result.success(status);
    }

    /**
     * 清除所有缓存
     */
    @DeleteMapping("/clear-all")
    public Result<String> clearAllCache() {
        try {
            cacheService.clearAllCache();
            return Result.success("所有缓存已清除");
        } catch (Exception e) {
            return Result.error("清除缓存失败: " + e.getMessage());
        }
    }

    /**
     * 清除文章相关缓存
     */
    @DeleteMapping("/clear-article")
    public Result<String> clearArticleCache() {
        try {
            cacheService.clearArticleCache();
            return Result.success("文章缓存已清除");
        } catch (Exception e) {
            return Result.error("清除文章缓存失败: " + e.getMessage());
        }
    }

    /**
     * 清除分类相关缓存
     */
    @DeleteMapping("/clear-category")
    public Result<String> clearCategoryCache() {
        try {
            cacheService.clearCategoryCache();
            return Result.success("分类缓存已清除");
        } catch (Exception e) {
            return Result.error("清除分类缓存失败: " + e.getMessage());
        }
    }

    /**
     * 设置缓存
     */
    @PostMapping("/set")
    public Result<String> setCache(@RequestParam String key, 
                                  @RequestParam String value, 
                                  @RequestParam(defaultValue = "3600") long expireTime) {
        try {
            boolean success = redisUtil.set(key, value, expireTime);
            if (success) {
                return Result.success("缓存设置成功");
            } else {
                return Result.error("缓存设置失败");
            }
        } catch (Exception e) {
            return Result.error("设置缓存失败: " + e.getMessage());
        }
    }

    /**
     * 获取缓存
     */
    @GetMapping("/get")
    public Result<Object> getCache(@RequestParam String key) {
        try {
            Object value = redisUtil.get(key);
            if (value != null) {
                return Result.success(value);
            } else {
                return Result.error("缓存不存在");
            }
        } catch (Exception e) {
            return Result.error("获取缓存失败: " + e.getMessage());
        }
    }

    /**
     * 删除缓存
     */
    @DeleteMapping("/delete")
    public Result<String> deleteCache(@RequestParam String key) {
        try {
            boolean success = redisUtil.delete(key);
            if (success) {
                return Result.success("缓存删除成功");
            } else {
                return Result.error("缓存删除失败");
            }
        } catch (Exception e) {
            return Result.error("删除缓存失败: " + e.getMessage());
        }
    }

    /**
     * 检查缓存是否存在
     */
    @GetMapping("/exists")
    public Result<Boolean> checkCacheExists(@RequestParam String key) {
        try {
            boolean exists = redisUtil.hasKey(key);
            return Result.success(exists);
        } catch (Exception e) {
            return Result.error("检查缓存失败: " + e.getMessage());
        }
    }

    /**
     * 获取缓存过期时间
     */
    @GetMapping("/expire")
    public Result<Long> getCacheExpireTime(@RequestParam String key) {
        try {
            long expireTime = redisUtil.getExpire(key);
            return Result.success(expireTime);
        } catch (Exception e) {
            return Result.error("获取过期时间失败: " + e.getMessage());
        }
    }
} 