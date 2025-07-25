package com.zk.controller;

import com.zk.pojo.Article;
import com.zk.pojo.PageBean;
import com.zk.pojo.Result;
import com.zk.service.ArticleService;
import com.zk.utils.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @program: big-event
 * @description:
 * @author: 张恺
 * @create: 2025-07-15 13:33
 * @version: 1.0
 **/
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/list")
    public Result<String> list(/*@RequestHeader("Authorization") String token, HttpServletResponse response*/){

        /*try {
            Map<String, Object> claims = JwtUtil.parseToken(token);
            return Result.success("所有的文章数据");
        } catch (Exception e) {
            response.setStatus(401);
            return Result.error("未登录");
       }*/
        return Result.success("所有的文章数据");
    }


    @PostMapping
    public Result<String> article(@RequestBody @Validated Article article){
        articleService.add(article);
        return Result.success("发布文章成功");
    }

    @GetMapping
    public Result<PageBean<Article>> list(Integer pageNum, Integer pageSize, @RequestParam(required = false) Integer categoryId, @RequestParam(required = false) String state){
        PageBean<Article> pb = articleService.list(pageNum, pageSize, categoryId, state);
        return Result.success(pb);
    }


    @PutMapping
    public Result<String> update(@RequestBody @Validated Article article) {
        articleService.update(article);
        return Result.success("更新文章成功");
    }

    @GetMapping("/detail")
    public Result<Article> detail(Integer id) {
        Article article = articleService.findById(id);
        if (article == null){
            return Result.error("文章不存在");
        }
        return Result.success(article);
    }

    @DeleteMapping
    public Result<String> delete(Integer id) {
        if(articleService.findById(id) == null){
            return Result.error("文章不存在");
        }

        articleService.delete(id);
        return Result.success("删除文章成功");
    }
}