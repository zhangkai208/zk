package com.zk.service;

import com.zk.pojo.Article;
import com.zk.pojo.PageBean;
import org.springframework.stereotype.Service;

/**
 * @program: big-event
 * @description:
 * @author: 张恺
 * @create: 2025-07-17 09:23
 * @version: 1.0
 **/

public interface ArticleService {
    void add(Article article);


    PageBean<Article> list(Integer pageNum, Integer pageSize, Integer categoryId, String state);

    void update(Article article);

    Article findById(Integer id);

    void delete(Integer id);
}