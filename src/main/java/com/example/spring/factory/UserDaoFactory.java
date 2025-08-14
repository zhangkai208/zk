package com.example.spring.factory;

import com.example.spring.dao.UserDao;
import com.example.spring.dao.impl.UserDaoImpl;

public class UserDaoFactory {
    public UserDao getUserDao() {
        return new UserDaoImpl();
    }
}
