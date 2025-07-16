package com.zk.controller;

import com.zk.pojo.Result;
import com.zk.utils.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
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


}