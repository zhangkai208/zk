package com.rabbitmq.service.impl;

import com.rabbitmq.config.RabbitMQConfig;
import com.rabbitmq.service.RabbitMQService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class RabbitMQServiceImpl implements RabbitMQService {
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public String sendMsg(String msg) throws Exception {
        try {
            String msgId = UUID.randomUUID().toString().replace("-", "").substring(0, 32);
            String sendTime = sdf.format(new Date());
            Map<String, Object> map = new HashMap<>();
            map.put("msgId", msgId);
            map.put("sendTime", sendTime);
            map.put("msg", msg);
            rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY, map);
            return "ok";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }
}