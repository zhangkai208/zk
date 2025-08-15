package com.example.spring.config;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@PropertySource("jdbc.properties")
@Configuration
@ComponentScan(basePackages = "com.example.spring")
public class SpringConfig {
}
