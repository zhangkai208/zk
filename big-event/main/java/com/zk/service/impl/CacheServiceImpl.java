package com.zk.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zk.pojo.Article;
import com.zk.pojo.Category;
import com.zk.pojo.PageBean;
import com.zk.service.CacheService;
import com.zk.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * 缓存服务实现类
 */
@Service
public class CacheServiceImpl implements CacheService {

    @Autowired
    private RedisUtil redisUtil;
    
    @Autowired
    private ObjectMapper objectMapper;

    private static final String ARTICLE_LIST_PREFIX = "article:list:";
    private static final String ARTICLE_DETAIL_PREFIX = "article:detail:";
    private static final String CATEGORY_LIST_KEY = "category:list";
    private static final String ARTICLE_KEYS_PREFIX = "article:keys";
    private static final String CATEGORY_KEYS_PREFIX = "category:keys";
    
    // 缓存过期时间（秒）
    private static final long ARTICLE_CACHE_EXPIRE = 3600; // 1小时
    private static final long CATEGORY_CACHE_EXPIRE = 7200; // 2小时

    @Override
    public void cacheArticleList(Integer pageNum, Integer pageSize, Integer categoryId, String state, PageBean<Article> pageBean) {
        try {
            String key = generateArticleListKey(pageNum, pageSize, categoryId, state);
            redisUtil.set(key, pageBean, ARTICLE_CACHE_EXPIRE);
            // 记录key用于后续清除缓存
            addToKeySet(ARTICLE_KEYS_PREFIX, key);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public PageBean<Article> getCachedArticleList(Integer pageNum, Integer pageSize, Integer categoryId, String state) {
        try {
            String key = generateArticleListKey(pageNum, pageSize, categoryId, state);
            Object cached = redisUtil.get(key);
            if (cached != null) {
                return objectMapper.convertValue(cached, new TypeReference<PageBean<Article>>() {});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void cacheArticleDetail(Integer id, Article article) {
        try {
            String key = ARTICLE_DETAIL_PREFIX + id;
            redisUtil.set(key, article, ARTICLE_CACHE_EXPIRE);
            addToKeySet(ARTICLE_KEYS_PREFIX, key);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Article getCachedArticleDetail(Integer id) {
        try {
            String key = ARTICLE_DETAIL_PREFIX + id;
            Object cached = redisUtil.get(key);
            if (cached != null) {
                return objectMapper.convertValue(cached, Article.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void cacheCategoryList(List<Category> categories) {
        try {
            redisUtil.set(CATEGORY_LIST_KEY, categories, CATEGORY_CACHE_EXPIRE);
            addToKeySet(CATEGORY_KEYS_PREFIX, CATEGORY_LIST_KEY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Category> getCachedCategoryList() {
        try {
            Object cached = redisUtil.get(CATEGORY_LIST_KEY);
            if (cached != null) {
                return objectMapper.convertValue(cached, new TypeReference<List<Category>>() {});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void clearArticleCache() {
        try {
            Set<String> keys = getKeySet(ARTICLE_KEYS_PREFIX);
            for (String key : keys) {
                redisUtil.delete(key);
            }
            redisUtil.delete(ARTICLE_KEYS_PREFIX);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void clearCategoryCache() {
        try {
            Set<String> keys = getKeySet(CATEGORY_KEYS_PREFIX);
            for (String key : keys) {
                redisUtil.delete(key);
            }
            redisUtil.delete(CATEGORY_KEYS_PREFIX);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void clearAllCache() {
        clearArticleCache();
        clearCategoryCache();
    }

    /**
     * 生成文章列表缓存key
     */
    private String generateArticleListKey(Integer pageNum, Integer pageSize, Integer categoryId, String state) {
        return ARTICLE_LIST_PREFIX + pageNum + ":" + pageSize + ":" + 
               (categoryId == null ? "null" : categoryId) + ":" + 
               (state == null ? "null" : state);
    }

    /**
     * 添加key到集合中
     */
    private void addToKeySet(String keySetName, String key) {
        try {
            // 这里简化实现，实际可以使用Redis Set
            String currentKeys = (String) redisUtil.get(keySetName);
            if (currentKeys == null) {
                currentKeys = key;
            } else {
                currentKeys += "," + key;
            }
            redisUtil.set(keySetName, currentKeys);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取key集合
     */
    private Set<String> getKeySet(String keySetName) {
        try {
            String keys = (String) redisUtil.get(keySetName);
            if (keys != null) {
                return Set.of(keys.split(","));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Set.of();
    }
} 