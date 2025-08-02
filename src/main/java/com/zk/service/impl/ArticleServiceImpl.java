package com.zk.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zk.mapper.ArticleMapper;
import com.zk.pojo.Article;
import com.zk.pojo.PageBean;
import com.zk.service.ArticleService;
import com.zk.service.CacheService;
import com.zk.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @program: big-event
 * @description:
 * @author: 张恺
 * @create: 2025-07-17 09:23
 * @version: 1.0
 **/
@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;
    
    @Autowired
    private CacheService cacheService;
    @Override
    public void add(Article article) {
        article.setCreateTime(LocalDateTime.now());
        article.setUpdateTime(LocalDateTime.now());
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        article.setCreateUser(id);
        articleMapper.add(article);
        
        // 清除相关缓存
        cacheService.clearArticleCache();
    }

    @Override
    public PageBean<Article> list(Integer pageNum, Integer pageSize, Integer categoryId, String state) {
        // 先尝试从缓存获取
        PageBean<Article> cachedResult = cacheService.getCachedArticleList(pageNum, pageSize, categoryId, state);
        if (cachedResult != null) {
            return cachedResult;
        }
        
        // 缓存未命中，从数据库查询
        PageBean<Article> pb = new PageBean<>();
        PageHelper.startPage(pageNum, pageSize);
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        List<Article> as = articleMapper.list(id, categoryId, state);
        Page<Article> page = (Page<Article>) as;
        pb.setTotal(page.getTotal());
        pb.setItems(page.getResult());
        
        // 将结果缓存
        cacheService.cacheArticleList(pageNum, pageSize, categoryId, state, pb);
        
        return pb;
    }

    @Override
    public void update(Article article) {
        article.setUpdateTime(LocalDateTime.now());
        articleMapper.update(article);
        
        // 清除相关缓存
        cacheService.clearArticleCache();
    }

    @Override
    public Article findById(Integer id) {
        // 先尝试从缓存获取
        Article cachedArticle = cacheService.getCachedArticleDetail(id);
        if (cachedArticle != null) {
            return cachedArticle;
        }
        
        // 缓存未命中，从数据库查询
        Article article = articleMapper.findById(id);
        if (article != null) {
            // 将结果缓存
            cacheService.cacheArticleDetail(id, article);
        }
        
        return article;
    }

    @Override
    public void delete(Integer id) {
        articleMapper.delete(id);
        
        // 清除相关缓存
        cacheService.clearArticleCache();
    }
}