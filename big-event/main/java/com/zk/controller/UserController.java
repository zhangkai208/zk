package com.zk.controller;

import com.zk.pojo.Result;
import com.zk.pojo.UpdatePwd;
import com.zk.pojo.User;
import com.zk.service.UserService;
import com.zk.utils.JwtUtil;
import com.zk.utils.Md5Util;
import com.zk.utils.ThreadLocalUtil;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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

    @Autowired
    private StringRedisTemplate stringredistemplate ;
    @PostMapping("/register")
    public Result register(@Validated User user) {
        /* if (username != null && password != null && username.length() > 5 && password.length() > 5  && username.length() < 16 && password.length() < 16) {
         */
        String username = user.getUsername();
        String password = user.getPassword();
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
    public Result login(@Validated User user) {
        String username = user.getUsername();
        String password = user.getPassword();

        User l = userService.findByUserName(username);
        if (l == null){
            return Result.error("用户不存在");
        }else if (Md5Util.getMD5String(password).equals(l.getPassword())){
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", l.getId());
            claims.put("username", l.getUsername());
            String token = JwtUtil.genToken(claims);
            ValueOperations<String, String> operations = stringredistemplate.opsForValue();
            operations.set(token,token,1, TimeUnit.HOURS);
            return Result.success(token);
        }
        return Result.error("密码错误");
    }
    @GetMapping("/userinfo")
    public Result<User> userinfo(/*@RequestHeader(name = "Authorization") String token*/){

      /*  Map<String, Object> map = JwtUtil.parseToken(token);
        String username = (String) map.get("username");*/

        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        User user = userService.findByUserName(username);
        return Result.success(user);
    }


    @PutMapping("/update")
    public Result update(@RequestBody @Validated User user){
        userService.update(user);
        return Result.success();
    }

    @PatchMapping("/updateAvatar")
    public Result updateAvatar(@RequestParam @URL String avatarUrl){
        userService.updateAvatar(avatarUrl);
        return Result.success();
    }

    @PatchMapping("/updatePwd")
    public Result updatePwd(@RequestBody  @Validated UpdatePwd updatePwd,@RequestHeader(name = "Authorization") String token){
        ValueOperations<String, String> operations = stringredistemplate.opsForValue();
        operations.getOperations().delete(token);

        String oldPwd = updatePwd.getOldPwd();
        String newPwd = updatePwd.getNewPwd();
        String rePwd = updatePwd.getRePwd();
        if (!StringUtils.hasLength(oldPwd) || !StringUtils.hasLength(newPwd) || !StringUtils.hasLength(rePwd) ){
            return Result.error("参数缺少");
        }

        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        User loginuser = userService.findByUserName(username);

        if (!Md5Util.getMD5String(oldPwd).equals(loginuser.getPassword())){
            return Result.error("原密码错误");}

        if (Md5Util.getMD5String(newPwd).equals(loginuser.getPassword()) || Md5Util.getMD5String(rePwd).equals(loginuser.getPassword())){
            return Result.error("不能和原密码一致");}

            if (newPwd.equals(rePwd)){
                userService.updatePwd(newPwd);
                return Result.success();
            }else{
                return Result.error("两次密码不一致");}
        }
    }
