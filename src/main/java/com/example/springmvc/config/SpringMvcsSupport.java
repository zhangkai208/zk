package com.example.springmvc.config;

import com.example.springmvc.interceptor.ProjectIntercepor;
import com.example.springmvc.interceptor.ProjectIntercepor2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SpringMvcsSupport implements WebMvcConfigurer {
    @Autowired
    private ProjectIntercepor projectIntercepor;

    @Autowired
    private ProjectIntercepor2 projectIntercepor2;
    /*@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/pages/**").addResourceLocations("classpath:/pages/");
    }*/

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(projectIntercepor).addPathPatterns("/books/**");
        registry.addInterceptor(projectIntercepor2).addPathPatterns("/books/**");
    }
}
