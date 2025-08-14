package com.example.spring;

import com.example.spring.dao.OrderDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Application3 {
    public static void main(String[] args) {
        /*OrderDao orderDao = OrderDaoFactory.getOrderDao();
        orderDao.save();*/
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        OrderDao orderDao = (OrderDao) context.getBean("orderDao");
        orderDao.save();
    }

}
