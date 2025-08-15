package com.example.spring;

import com.example.spring.config.SpringConfig;
import com.example.spring.dao.BookDao;
import com.example.spring.service.BookService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application10 {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        BookDao bookDao = context.getBean(BookDao.class);
        System.out.println(bookDao);
        BookService bookService = context.getBean(BookService.class);
        System.out.println(bookService);
    }
}
