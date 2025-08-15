package com.example.mybatis;

import com.example.mybatis.config.SpringConfig;
import com.example.mybatis.domain.Account;
import com.example.mybatis.service.AccountService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        AccountService accountService = context.getBean(AccountService.class);
        Account byId = accountService.findById(1);
        System.out.println(byId);

    }
}
