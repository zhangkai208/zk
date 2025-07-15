package com.zk.controller;

import com.zk.pojo.Result;
import com.zk.pojo.User;
import com.zk.service.UserService;
import com.zk.utils.JwtUtil;
import com.zk.utils.Md5Util;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: big-event
 * @description:
 * @author: 张恺
 * @create: 2025-07-15 09:54
 * @version: 1.0
 **/
@Validated
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Result register(@Pattern(regexp = "^.{5,16}$") String username, @Pattern(regexp = "^.{5,16}$") String password) {
        /* if (username != null && password != null && username.length() > 5 && password.length() > 5  && username.length() < 16 && password.length() < 16) {
         */
        User u = userService.findByUserName(username);
        if (u == null) {
            userService.register(username, password);
            return Result.success();
        } else {
            return Result.error("用户被占用");
        }
    } /*else
            return Result.error("参数不合法");
    }*/

    @PostMapping("/login")
    public Result login(@Pattern(regexp = "^.{5,16}$") String username, @Pattern(regexp = "^.{5,16}$") String password) {
        User l = userService.findByUserName(username);
        if (l == null){
            return Result.error("用户不存在");
        }else if (Md5Util.getMD5String(password).equals(l.getPassword())){
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", l.getId());
            claims.put("username", l.getUsername());
            String token = JwtUtil.genToken(claims);
            return Result.success(token);
        }
        return Result.error("密码错误");
    }
}