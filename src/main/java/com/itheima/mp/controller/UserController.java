package com.itheima.mp.controller;

import cn.hutool.core.bean.BeanUtil;
import com.itheima.mp.domain.dto.UserFormDTO;
import com.itheima.mp.domain.po.User;
import com.itheima.mp.query.UserQuery;
import com.itheima.mp.service.impl.UserServiceImpl;
import com.itheima.mp.domain.vo.UserVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "用户管理", description = "用户相关接口")
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
private UserServiceImpl userService;

    @Operation(summary = "新增用户")
    @PostMapping
    public void saveUser(@RequestBody UserFormDTO userFormDTO) {

        User user = BeanUtil.copyProperties(userFormDTO, User.class);
        userService.save(user);
    }

    @Operation(summary = "删除用户")
    @DeleteMapping("/{id}")
    public void deleteUserById(@Parameter(description = "用户ID") @PathVariable("id") Long id) {
        userService.removeById(id);
    }

    @Operation(summary = "根据id用户查询")
    @GetMapping("/{id}")
    public UserVO queryUserById(@Parameter(description = "用户ID") @PathVariable("id") Long id) {
        User user = userService.getById(id);
        return BeanUtil.copyProperties(user, UserVO.class);
    }

    @Operation(summary = "根据id多用户查询")
    @GetMapping
    public List<UserVO> queryUserById(@Parameter(description = "用户ID集合") @RequestParam("ids") List<Long> ids) {
        List<User> users = userService.listByIds(ids);
        return BeanUtil.copyToList(users, UserVO.class);
    }

    @Operation(summary = "扣减用户余额")
    @PutMapping("/{id}/deduction/{amount}")
    public void deductMoeyById(
            @Parameter(description = "用户ID") @PathVariable("id") Long id,
            @Parameter(description = "扣减用户余额") @PathVariable("amount") Integer amount) {
        userService.deductMoeyById(id, amount);
    }

    @Operation(summary = "根据条件用户查询")
    @GetMapping("/list")
    public List<UserVO> queryUser(UserQuery query) {
        List<User> users = userService.queryUser(query.getName(), query.getStatus(), query.getMinBalance(), query.getMaxBalance());
        return BeanUtil.copyToList(users, UserVO.class);
    }
} 