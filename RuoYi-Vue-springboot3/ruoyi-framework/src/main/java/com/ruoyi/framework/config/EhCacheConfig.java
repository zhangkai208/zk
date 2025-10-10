package com.ruoyi.framework.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.CacheManager;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;

import javax.cache.Caching;
import javax.cache.spi.CachingProvider;
import java.net.URI;

/**
 * EhCache 3.x 配置类
 * 使用 JSR-107 (JCache) 标准集成 EhCache 3.x 到 Spring Boot 3
 *
 * @author ruoyi
 */
@Configuration
@ConditionalOnProperty(prefix = "spring.cache", name = {"type"}, havingValue = "jcache", matchIfMissing = false)
public class EhCacheConfig {

    /**
     * 主要的缓存管理器
     * 使用 JCache 标准接口管理 EhCache 3.x
     */
    @Bean
    @Primary
    public CacheManager cacheManager() {
        try {
            // 获取 EhCache 的 JCache 提供者
            CachingProvider cachingProvider = Caching.getCachingProvider("org.ehcache.jsr107.EhcacheCachingProvider");

            // 获取配置文件 URI
            ClassPathResource resource = new ClassPathResource("ehcache.xml");
            URI configUri = resource.getURI();

            // 使用默认的URI和ClassLoader获取缓存管理器
            // 这样可以确保重用已存在的持久化缓存管理器实例
            javax.cache.CacheManager jCacheManager = cachingProvider.getCacheManager(
                    configUri,
                    getClass().getClassLoader(),
                    cachingProvider.getDefaultProperties()
            );

            // 包装为 Spring 的缓存管理器
            return new JCacheCacheManager(jCacheManager);
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize EhCache manager", e);
        }
    }
}
