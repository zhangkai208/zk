package com.example.baidu.service.impl;

import com.example.baidu.dao.ResourcesDao;
import com.example.baidu.service.ResourcesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResourcesServiceImpl implements ResourcesService {

    @Autowired
    private ResourcesDao resourcesDao;
    @Override
    public boolean openURL(String url, String password) {

        return resourcesDao.readResources(url,password);
    }
}