package com.ruoyi.common.core.cache;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

/**
 * spring cache 工具类 (基于EhCache)
 *
 * @author ruoyi
 **/
@Component
public class EhCacheUtil
{
    @Autowired
    public CacheManager cacheManager;

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param cacheName 缓存名称
     * @param key 缓存的键值
     * @param value 缓存的值
     */
    public <T> void setCacheObject(final String cacheName, final String key, final T value)
    {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            cache.put(key, value);
        }
    }

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param cacheName 缓存名称
     * @param key 缓存的键值
     * @param value 缓存的值
     * @param timeout 时间
     * @param timeUnit 时间颗粒度
     */
    public <T> void setCacheObject(final String cacheName, final String key, final T value, final Integer timeout, final TimeUnit timeUnit)
    {
        // EhCache不支持动态过期时间，需要在配置文件中设置
        setCacheObject(cacheName, key, value);
    }

    /**
     * 设置有效时间
     *
     * @param cacheName 缓存名称
     * @param key 缓存键
     * @param timeout 超时时间
     * @return true=设置成功；false=设置失败
     */
    public boolean expire(final String cacheName, final String key, final long timeout)
    {
        return expire(cacheName, key, timeout, TimeUnit.SECONDS);
    }

    /**
     * 设置有效时间
     *
     * @param cacheName 缓存名称
     * @param key 缓存键
     * @param timeout 超时时间
     * @param unit 时间单位
     * @return true=设置成功；false=设置失败
     */
    public boolean expire(final String cacheName, final String key, final long timeout, final TimeUnit unit)
    {
        // EhCache不支持动态过期时间，需要在配置文件中设置
        return true;
    }

    /**
     * 获取有效时间
     *
     * @param cacheName 缓存名称
     * @param key 缓存键
     * @return 有效时间
     */
    public long getExpire(final String cacheName, final String key)
    {
        // EhCache不支持动态获取过期时间
        return -1;
    }

    /**
     * 判断 key是否存在
     *
     * @param cacheName 缓存名称
     * @param key 键
     * @return true 存在 false不存在
     */
    public Boolean hasKey(final String cacheName, final String key)
    {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            return cache.get(key) != null;
        }
        return false;
    }

    /**
     * 获得缓存的基本对象。
     *
     * @param cacheName 缓存名称
     * @param key 缓存键值
     * @return 缓存键值对应的数据
     */
    @SuppressWarnings("unchecked")
    public <T> T getCacheObject(final String cacheName, final String key)
    {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            Cache.ValueWrapper valueWrapper = cache.get(key);
            if (valueWrapper != null) {
                return (T) valueWrapper.get();
            }
        }
        return null;
    }

    /**
     * 删除单个对象
     *
     * @param cacheName 缓存名称
     * @param key
     */
    public boolean deleteObject(final String cacheName, final String key)
    {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            cache.evict(key);
            return true;
        }
        return false;
    }

    /**
     * 删除集合对象
     *
     * @param cacheName 缓存名称
     * @param keys 多个对象
     * @return
     */
    public boolean deleteObject(final String cacheName, final Collection<String> keys)
    {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            for (String key : keys) {
                cache.evict(key);
            }
            return true;
        }
        return false;
    }

    /**
     * 缓存List数据
     *
     * @param cacheName 缓存名称
     * @param key 缓存的键值
     * @param dataList 待缓存的List数据
     * @return 缓存的对象
     */
    public <T> long setCacheList(final String cacheName, final String key, final List<T> dataList)
    {
        setCacheObject(cacheName, key, dataList);
        return dataList.size();
    }

    /**
     * 获得缓存的list对象
     *
     * @param cacheName 缓存名称
     * @param key 缓存的键值
     * @return 缓存键值对应的数据
     */
    public <T> List<T> getCacheList(final String cacheName, final String key)
    {
        return getCacheObject(cacheName, key);
    }

    /**
     * 缓存Set
     *
     * @param cacheName 缓存名称
     * @param key 缓存键值
     * @param dataSet 缓存的数据
     * @return 缓存数据的对象
     */
    public <T> long setCacheSet(final String cacheName, final String key, final Set<T> dataSet)
    {
        setCacheObject(cacheName, key, dataSet);
        return dataSet.size();
    }

    /**
     * 获得缓存的set
     *
     * @param cacheName 缓存名称
     * @param key
     * @return
     */
    public <T> Set<T> getCacheSet(final String cacheName, final String key)
    {
        return getCacheObject(cacheName, key);
    }

    /**
     * 缓存Map
     *
     * @param cacheName 缓存名称
     * @param key
     * @param dataMap
     */
    public <T> void setCacheMap(final String cacheName, final String key, final Map<String, T> dataMap)
    {
        if (dataMap != null) {
            setCacheObject(cacheName, key, dataMap);
        }
    }

    /**
     * 获得缓存的Map
     *
     * @param cacheName 缓存名称
     * @param key
     * @return
     */
    public <T> Map<String, T> getCacheMap(final String cacheName, final String key)
    {
        return getCacheObject(cacheName, key);
    }

    /**
     * 往Hash中存入数据
     *
     * @param cacheName 缓存名称
     * @param key Cache键
     * @param hKey Hash键
     * @param value 值
     */
    public <T> void setCacheMapValue(final String cacheName, final String key, final String hKey, final T value)
    {
        Map<String, T> map = getCacheObject(cacheName, key);
        if (map != null) {
            map.put(hKey, value);
            setCacheObject(cacheName, key, map);
        }
    }

    /**
     * 获取Hash中的数据
     *
     * @param cacheName 缓存名称
     * @param key Cache键
     * @param hKey Hash键
     * @return Hash中的对象
     */
    public <T> T getCacheMapValue(final String cacheName, final String key, final String hKey)
    {
        Map<String, T> map = getCacheObject(cacheName, key);
        if (map != null) {
            return map.get(hKey);
        }
        return null;
    }

    /**
     * 获取多个Hash中的数据
     *
     * @param cacheName 缓存名称
     * @param key Cache键
     * @param hKeys Hash键集合
     * @return Hash对象集合
     */
    public <T> List<T> getMultiCacheMapValue(final String cacheName, final String key, final Collection<String> hKeys)
    {
        Map<String, T> map = getCacheObject(cacheName, key);
        if (map != null) {
            return hKeys.stream().map(map::get).toList();
        }
        return List.of();
    }

    /**
     * 删除Hash中的某条数据
     *
     * @param cacheName 缓存名称
     * @param key Cache键
     * @param hKey Hash键
     * @return 是否成功
     */
    public boolean deleteCacheMapValue(final String cacheName, final String key, final String hKey)
    {
        Map<String, Object> map = getCacheObject(cacheName, key);
        if (map != null) {
            map.remove(hKey);
            setCacheObject(cacheName, key, map);
            return true;
        }
        return false;
    }

    /**
     * 获得缓存的基本对象列表
     *
     * @param cacheName 缓存名称
     * @param pattern 字符串前缀
     * @return 对象列表
     */
    public Collection<String> keys(final String cacheName, final String pattern)
    {
        // EhCache不支持keys操作，返回空集合
        return List.of();
    }

    /**
     * 清空指定缓存
     *
     * @param cacheName 缓存名称
     */
    public void clearCache(final String cacheName)
    {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        }
    }
}
