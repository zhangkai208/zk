package com.example.spring.dao.impl;

import com.example.spring.dao.UserDao;

public class UserDaoImpl implements UserDao {
    @Override
    public void save() {
        System.out.println("save user");
    }
}
