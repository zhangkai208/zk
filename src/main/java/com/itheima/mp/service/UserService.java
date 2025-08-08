package com.itheima.mp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.mp.domain.po.User;

import java.util.List;

public interface UserService extends IService<User> {
    void deductMoeyById(Long id, Integer amount);

    List<User> queryUser(String name, Integer status, Integer minBalance, Integer maxBalance);
} 