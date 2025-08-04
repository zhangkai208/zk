package com.zk.service;

import com.zk.pojo.Article;
import com.zk.pojo.Category;
import com.zk.pojo.PageBean;

import java.util.List;

/**
 * 缓存服务接口
 */
public interface CacheService {
    
    /**
     * 缓存文章列表
     */
    void cacheArticleList(Integer pageNum, Integer pageSize, Integer categoryId, String state, PageBean<Article> pageBean);
    
    /**
     * 获取缓存的文章列表
     */
    PageBean<Article> getCachedArticleList(Integer pageNum, Integer pageSize, Integer categoryId, String state);
    
    /**
     * 缓存文章详情
     */
    void cacheArticleDetail(Integer id, Article article);
    
    /**
     * 获取缓存的文章详情
     */
    Article getCachedArticleDetail(Integer id);
    
    /**
     * 缓存分类列表
     */
    void cacheCategoryList(List<Category> categories);
    
    /**
     * 获取缓存的分类列表
     */
    List<Category> getCachedCategoryList();
    
    /**
     * 清除文章相关缓存
     */
    void clearArticleCache();
    
    /**
     * 清除分类相关缓存
     */
    void clearCategoryCache();
    
    /**
     * 清除所有缓存
     */
    void clearAllCache();
} 