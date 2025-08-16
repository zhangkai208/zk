package com.example.springboot.controller;


import com.example.springboot.domain.Enterprise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
// @RestController = @Controller + @ResponseBody
@RequestMapping("/book")
public class BookController {
    //1
    @Value("${server.port}")
    private Integer port;
    @Value("${enterprise.subject[0]}")
    private String subject;
    @Autowired
    private Environment env;

    @Autowired
    private Enterprise enterprise;
    @GetMapping("/{id}")
    public String getById(@PathVariable Integer id){
        System.out.println("subject = " + subject);
        System.out.println("port = " + port);
        System.out.println("id = " + id);
        //2
        System.out.println("env = " + env.getProperty("enterprise.subject[0]"));
        System.out.println("enterprise = " + enterprise);
        return "hello";
    }
}
