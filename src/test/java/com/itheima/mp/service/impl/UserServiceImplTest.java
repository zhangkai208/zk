package com.itheima.mp.service.impl;

import com.itheima.mp.domain.po.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
class UserServiceImplTest {

    @Autowired
    private UserServiceImpl userService;

    @Test
    void testSaveUser() {

            User user = new User();
            /*user.setId(5L); // 使用不存在的ID*/
            user.setUsername("zk"); // 使用不存在的用户名
            user.setPassword("123");
            user.setPhone("18688990011");
            user.setBalance(200);
            user.setInfo("{\"age\": 24, \"intro\": \"英文老师\", \"gender\": \"female\"}");
            user.setCreateTime(LocalDateTime.now());
            user.setUpdateTime(LocalDateTime.now());
        userService.save(user);
    }


} 