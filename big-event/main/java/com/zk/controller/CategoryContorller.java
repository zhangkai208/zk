package com.zk.controller;

import com.zk.pojo.Category;
import com.zk.pojo.Result;
import com.zk.service.CategoryService;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: big-event
 * @description:
 * @author: 张恺
 * @create: 2025-07-16 14:13
 * @version: 1.0
 **/
@RestController
@RequestMapping("/category")
public class CategoryContorller {
    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public Result add(@RequestBody @Validated(Category.Add.class) Category category){
        categoryService.add(category);
        return Result.success();
    }


    @GetMapping
    public Result<List<Category>> list(){
        List<Category> cs = categoryService.list();
        return Result.success(cs);

    }

    @GetMapping("/detail")
    public Result<Category> detail(Integer id){
        Category c = categoryService.findById(id);
        if (c == null) {
            return Result.error("id不存在");
        }
        return Result.success(c);
    }

    @DeleteMapping
    public Result delete(Integer id){

        boolean deleted = categoryService.delete(id);
        if (deleted) {
            categoryService.delete(id);
            return Result.success();
        } else {
            return Result.error("id不存在");
        }
    }

    @PutMapping
    public Result update(@RequestBody @Validated(Category.Update.class) Category category){
        categoryService.update(category);
        return Result.success();
    }
}