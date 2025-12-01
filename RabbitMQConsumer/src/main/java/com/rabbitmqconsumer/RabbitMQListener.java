package com.rabbitmqconsumer;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RabbitListener(queues = {"queue1", "queue2"})
public class RabbitMQListener {
    @RabbitHandler
    public void process(Map map) {
        System.out.println("接收到消息：" + map.toString());
    }
}
