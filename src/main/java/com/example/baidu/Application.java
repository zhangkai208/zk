package com.example.baidu;

import com.example.baidu.service.ResourcesService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.example.baidu.config.SpringConfig;
public class Application {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        ResourcesService resourcesService = context.getBean(ResourcesService.class);
        boolean result = resourcesService.openURL("http://www.baidu.com","123456  ");
        System.out.println(result);


    }
}
