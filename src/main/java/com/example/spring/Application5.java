package com.example.spring;

import com.example.spring.dao.BookDao;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.ApplicationContext;

public class Application5 {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        context.registerShutdownHook();//关闭钩子,要等运行完成后
        BookDao bookDao = context.getBean("bookDao", BookDao.class);
        bookDao.save();
        /*context.close();*///直接关闭
    }
}
