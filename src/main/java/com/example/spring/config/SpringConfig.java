package com.example.spring.config;


import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.context.annotation.*;

import javax.sql.DataSource;

@Import({JdbcConfig.class})
@PropertySource("jdbc.properties")
@EnableAspectJAutoProxy(proxyTargetClass = true)
@Configuration
@ComponentScan(basePackages = "com.example.spring")
public class SpringConfig {


}
