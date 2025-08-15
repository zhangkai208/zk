package com.example.mybatis.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class JdbcConfig {
    @Value("${jdbc.username}")
    private String Username;
    @Value("${jdbc.password}")
    private String Password;
    @Value("${jdbc.url}")
    private String Url;
    @Value("${jdbc.driverClassName}")
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
