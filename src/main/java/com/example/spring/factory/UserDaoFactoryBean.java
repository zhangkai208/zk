package com.example.spring.factory;

import com.example.spring.dao.UserDao;
import com.example.spring.dao.impl.UserDaoImpl;
import org.springframework.beans.factory.FactoryBean;

public class UserDaoFactoryBean implements FactoryBean<UserDao> {

    @Override
    public UserDao getObject() throws Exception {
        return new UserDaoImpl();
    }

    @Override
    public Class<?> getObjectType() {
        return UserDao.class;
    }
//true为单例
    @Override
    public boolean isSingleton() {
        return true;
    }
}
