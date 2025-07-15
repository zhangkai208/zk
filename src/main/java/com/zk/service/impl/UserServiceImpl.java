package com.zk.service.impl;

import com.zk.mapper.UserMapper;
import com.zk.pojo.User;
import com.zk.service.UserService;
import com.zk.utils.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: big-event
 * @description:
 * @author: 张恺
 * @create: 2025-07-15 09:55
 * @version: 1.0
 **/
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public User findByUserName(String username) {
        User u = userMapper.findByUserName(username);
        return u;
    }

    @Override
    public void register(String username, String password) {
        String md5String = Md5Util.getMD5String(password);

        userMapper.add(username,md5String);
    }
}