package com.example.spring.dao.impl;

import com.example.spring.dao.OrderDao;

public class OrderDaoImpl implements OrderDao {
    @Override
    public void save() {
        System.out.println("OrderDaoImpl save()");
    }
}
