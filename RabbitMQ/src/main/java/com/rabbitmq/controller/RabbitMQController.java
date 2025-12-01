package com.rabbitmq.controller;

import com.rabbitmq.service.RabbitMQService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rabbitmq")
public class RabbitMQController {
    @Autowired
    private RabbitMQService rabbitMQService;
    @PostMapping("/sendMsg")
    public String sendMsg(@RequestParam(name = "msg") String msg) throws Exception {
        return rabbitMQService.sendMsg(msg);
    }
    @PostMapping("/publish")
    public String publish(@RequestParam(name = "msg") String msg) throws Exception {
        return rabbitMQService.sendFanoutMsg(msg);
    }
    
    @PostMapping("/sendTopicMsg")
    public String sendTopicMsg(@RequestParam(name = "msg") String msg,
                               @RequestParam(name = "routingKey") String routingKey) throws Exception {
        return rabbitMQService.sendTopicMsg(msg, routingKey);
    }
}
