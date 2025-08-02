package com.zk.service.impl;

import com.zk.mapper.CategoryMapper;
import com.zk.pojo.Category;
import com.zk.service.CacheService;
import com.zk.service.CategoryService;
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
 * @create: 2025-07-16 14:14
 * @version: 1.0
 **/
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    
    @Autowired
    private CacheService cacheService;

    @Override
    public void add(Category category) {
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        category.setCreateUser(id);
        categoryMapper.add(category);
        
        // 清除相关缓存
        cacheService.clearCategoryCache();
    }

    @Override
    public List<Category> list() {
        // 先尝试从缓存获取
        List<Category> cachedCategories = cacheService.getCachedCategoryList();
        if (cachedCategories != null) {
            return cachedCategories;
        }
        
        // 缓存未命中，从数据库查询
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        List<Category> categories = categoryMapper.list(id);
        
        // 将结果缓存
        cacheService.cacheCategoryList(categories);
        
        return categories;
    }

    @Override
    public Category findById(Integer id) {

        Category c = categoryMapper.findById(id);
        return c;
    }

    @Override
    public boolean delete(Integer id) {
        Category category = categoryMapper.findById(id); // 这里查数据库
        if (category == null) {
            return false; // 数据库没有这个id
        }
        categoryMapper.delete(id); // 数据库有，执行删除
        
        // 清除相关缓存
        cacheService.clearCategoryCache();
        
        return true;
    }

    @Override
    public void update(Category category) {
        category.setUpdateTime(LocalDateTime.now());
        categoryMapper.update(category);
        
        // 清除相关缓存
        cacheService.clearCategoryCache();
    }
}