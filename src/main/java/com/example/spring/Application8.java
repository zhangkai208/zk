package com.example.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import javax.sql.DataSource;

public class Application8 {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        ApplicationContext context2 = new FileSystemXmlApplicationContext("C:\\Users\\张恺\\Desktop\\Spring\\src\\main\\resources\\applicationContext.xml");
        DataSource dataSource = context2.getBean("dataSource", DataSource.class);
        System.out.println(dataSource);
    }
}
