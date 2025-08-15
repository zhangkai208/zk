package com.example.spring.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.example.spring.dao.BookDao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class JdbcConfig {
    @Value("root")
    private String Username;
    @Value("123456")
    private String Password;
    @Value("jdbc:mysql://localhost:3306/test")
    private String Url;
    @Value("com.mysql.jdbc.Driver")
    private String DriverClassName;

    @Bean
    public DataSource dataSource(){
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUsername(Username);
        dataSource.setPassword(Password);
        dataSource.setUrl(Url);
        dataSource.setDriverClassName(DriverClassName);
        return dataSource;
    }
}
