package com.itheima.mp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.mp.domain.po.User;
import com.itheima.mp.mapper.UserMapper;
import com.itheima.mp.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService {

    @Override
    @Transactional
    public void deductMoeyById(Long id, Integer amount) {
        User user = getById(id);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        if (user.getStatus() == 2) {
            throw new RuntimeException("用户已冻结");
        }
        if (user.getBalance() < amount) {
            throw new RuntimeException("余额不足");
        }
        int rebalance = user.getBalance() - amount;
        lambdaUpdate()
                .set(User::getBalance,rebalance)
                .set(rebalance ==  0,User::getStatus, 2)
                .eq(User::getId,id)
                .eq(User::getBalance,user.getBalance())
                .update();
    }

    @Override
    public List<User> queryUser(String name, Integer status, Integer minBalance, Integer maxBalance) {
        return lambdaQuery()
                .like(name != null, User::getUsername, name)
                .eq(status != null, User::getStatus, status)
                .ge(minBalance != null, User::getBalance, minBalance)
                .le(maxBalance != null, User::getBalance, maxBalance)
                .list();

    }
} 