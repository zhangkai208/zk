package com.zk;

import com.zk.utils.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Redis功能测试
 */
@SpringBootTest
public class RedisTest {

    @Autowired
    private RedisUtil redisUtil;

    @Test
    public void testRedisConnection() {
        // 测试Redis连接
        String testKey = "test:connection";
        String testValue = "test_value";
        
        // 设置缓存
        boolean setResult = redisUtil.set(testKey, testValue, 60);
        assertTrue(setResult, "Redis设置缓存失败");
        
        // 获取缓存
        Object result = redisUtil.get(testKey);
        assertEquals(testValue, result, "Redis获取缓存失败");
        
        // 检查key是否存在
        boolean exists = redisUtil.hasKey(testKey);
        assertTrue(exists, "Redis检查key存在失败");
        
        // 获取过期时间
        long expireTime = redisUtil.getExpire(testKey);
        assertTrue(expireTime > 0, "Redis获取过期时间失败");
        
        // 删除缓存
        boolean deleteResult = redisUtil.delete(testKey);
        assertTrue(deleteResult, "Redis删除缓存失败");
        
        // 验证删除成功
        Object deletedResult = redisUtil.get(testKey);
        assertNull(deletedResult, "Redis删除缓存验证失败");
    }

    @Test
    public void testRedisExpire() {
        String testKey = "test:expire";
        String testValue = "test_value";
        
        // 设置缓存，过期时间5秒
        boolean setResult = redisUtil.set(testKey, testValue, 5);
        assertTrue(setResult, "Redis设置缓存失败");
        
        // 验证过期时间
        long expireTime = redisUtil.getExpire(testKey);
        assertTrue(expireTime > 0 && expireTime <= 5, "Redis过期时间设置失败");
        
        // 清理测试数据
        redisUtil.delete(testKey);
    }
}