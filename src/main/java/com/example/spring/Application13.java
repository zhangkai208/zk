package com.example.spring;

import com.example.spring.config.SpringConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.sql.DataSource;

public class Application13 {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        DataSource bean = context.getBean(DataSource.class);
        System.out.println(bean);
    }
}
