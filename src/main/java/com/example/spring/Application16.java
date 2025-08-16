package com.example.spring;

import com.example.spring.config.SpringConfig;
import com.example.spring.dao.impl.BookDaoImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application16 {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        BookDaoImpl bookDao = context.getBean(BookDaoImpl.class);
        String name = bookDao.findName(10);
        System.out.println(name);
    }
}
