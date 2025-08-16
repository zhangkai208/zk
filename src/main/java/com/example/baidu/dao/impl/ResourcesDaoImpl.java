package com.example.baidu.dao.impl;

import com.example.baidu.dao.ResourcesDao;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
public class ResourcesDaoImpl implements ResourcesDao {


    @Override
    public boolean readResources(String url, String password) {
        return password.equals("123456");
    }
}
