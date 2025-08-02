package com.zk.service;

import com.zk.pojo.Category;

import java.util.List;

/**
 * @program: big-event
 * @description:
 * @author: 张恺
 * @create: 2025-07-16 14:13
 * @version: 1.0
 **/
public interface CategoryService {
    void add(Category category);

    List<Category> list();

    Category findById(Integer id);

    boolean delete(Integer id);

    void update(Category category);
}