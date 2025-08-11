package com.example.jwtdome;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.jwtdome.mapper")
public class JwtDomeApplication {

    public static void main(String[] args) {

        SpringApplication.run(JwtDomeApplication.class, args);
        System.out.println("启动成功");
    }

}
