package com.example.spring;

import com.example.spring.dao.BookDao;
import com.example.spring.service.BookService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Application9 {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        BookService bookService = context.getBean(BookService.class);
        bookService.save();
        System.out.println(bookService);
        /*BookDao bookDao = context.getBean("bookDao", BookDao.class);
        bookDao.save();*/
    }
}
