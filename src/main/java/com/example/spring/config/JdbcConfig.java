package com.example.spring.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import javax.sql.DataSource;


public class JdbcConfig {
    @Bean
    public DataSource dataSource(){
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUsername("root");
        dataSource.setPassword("123456");
        dataSource.setUrl("jdbc:mysql://localhost:3306/test");
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        return (DataSource) dataSource;
    }
}
