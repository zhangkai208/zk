package com.zk.service;

import com.zk.pojo.User;

/**
 * @program: big-event
 * @description:
 * @author: 张恺
 * @create: 2025-07-15 09:55
 * @version: 1.0
 **/
public interface UserService {

    public User findByUserName(String username);

    public void register(String username, String password);
}