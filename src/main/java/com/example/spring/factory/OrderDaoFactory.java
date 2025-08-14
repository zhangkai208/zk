package com.example.spring.factory;

import com.example.spring.dao.OrderDao;
import com.example.spring.dao.impl.OrderDaoImpl;

public class OrderDaoFactory {
    public static OrderDao getOrderDao(){
        return new OrderDaoImpl();
    }
}
