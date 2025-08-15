package com.example.spring;

import com.example.spring.config.SpringConfig;
import com.example.spring.dao.BookDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

public class Application11 {
    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringConfig.class);
        BookDao bookDao = ctx.getBean(BookDao.class);
        BookDao bookDao1 = ctx.getBean(BookDao.class);
        System.out.println(bookDao);
        System.out.println(bookDao1);
        ((AbstractApplicationContext) ctx).close();
    }
}
