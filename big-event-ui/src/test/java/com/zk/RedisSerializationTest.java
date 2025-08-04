package com.zk;

import com.zk.pojo.Category;
import com.zk.utils.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Redis序列化测试
 */
@SpringBootTest
public class RedisSerializationTest {

    @Autowired
    private RedisUtil redisUtil;

    @Test
    public void testLocalDateTimeSerialization() {
        // 创建测试数据
        Category category = new Category();
        category.setId(1);
        category.setCategoryName("测试分类");
        category.setCategoryAlias("test");
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());
        category.setCreateUser(1);

        List<Category> categories = new ArrayList<>();
        categories.add(category);

        String testKey = "test:category:serialization";

        try {
            // 测试序列化到Redis
            boolean setResult = redisUtil.set(testKey, categories, 60);
            assertTrue(setResult, "序列化到Redis失败");

            // 测试从Redis反序列化
            Object cached = redisUtil.get(testKey);
            assertNotNull(cached, "从Redis获取数据失败");

            // 验证数据类型
            assertTrue(cached instanceof List, "反序列化后的数据类型不正确");

            @SuppressWarnings("unchecked")
            List<Category> cachedCategories = (List<Category>) cached;
            assertFalse(cachedCategories.isEmpty(), "反序列化后的列表为空");

            Category cachedCategory = cachedCategories.get(0);
            assertEquals(category.getId(), cachedCategory.getId(), "ID不匹配");
            assertEquals(category.getCategoryName(), cachedCategory.getCategoryName(), "分类名称不匹配");
            assertNotNull(cachedCategory.getCreateTime(), "创建时间序列化失败");
            assertNotNull(cachedCategory.getUpdateTime(), "更新时间序列化失败");

            System.out.println("✅ LocalDateTime序列化测试通过");
            System.out.println("原始创建时间: " + category.getCreateTime());
            System.out.println("缓存创建时间: " + cachedCategory.getCreateTime());

        } finally {
            // 清理测试数据
            redisUtil.delete(testKey);
        }
    }

    @Test
    public void testRedisConnection() {
        String testKey = "test:connection";
        String testValue = "test_value";
        
        try {
            // 测试基本连接
            boolean setResult = redisUtil.set(testKey, testValue, 60);
            assertTrue(setResult, "Redis连接失败");
            
            Object result = redisUtil.get(testKey);
            assertEquals(testValue, result, "Redis读写测试失败");
            
            System.out.println("✅ Redis连接测试通过");
            
        } finally {
            redisUtil.delete(testKey);
        }
    }
} 