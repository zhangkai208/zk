package com.rabbitmq.service.impl;

import com.rabbitmq.config.RabbitMQConfig;
import com.rabbitmq.service.RabbitMQService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
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
            Map<String, Object> message = getMessage(msg);
            rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY, message);
            return "ok";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }
    
    @Override
    public String sendFanoutMsg(String msg) throws Exception {
        try {
            Map<String, Object> message = getMessage(msg);
            rabbitTemplate.convertAndSend(RabbitMQConfig.FANOUT_EXCHANGE_NAME, "", message);
            return "ok";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }
    
    @Override
    public String sendTopicMsg(String msg, String routingKey) throws Exception {
        try {
            Map<String, Object> message = getMessage(msg);
            // 发送到TopicExchange，使用指定的routing key
            rabbitTemplate.convertAndSend(RabbitMQConfig.TOPIC_EXCHANGE_NAME, routingKey, message);
            return "ok";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }
    
    @Override
    public String sendHeadersMsg(String msg, Map<String, Object> headers) throws Exception {
        try {
            Map<String, Object> message = getMessage(msg);
            // 发送到HeadersExchange，使用headers进行路由
            rabbitTemplate.convertAndSend(RabbitMQConfig.HEADERS_EXCHANGE_NAME, "", message, new MessagePostProcessor() {
                @Override
                public Message postProcessMessage(Message msg) throws org.springframework.amqp.AmqpException {
                    MessageProperties props = msg.getMessageProperties();
                    // 设置自定义headers
                    if (headers != null) {
                        headers.forEach((key, value) -> props.setHeader(key, value));
                    }
                    return msg;
                }
            });
            return "ok";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }
    
    //组装消息体
    private Map<String, Object> getMessage(String msg) {
        String msgId = UUID.randomUUID().toString().replace("-", "").substring(0, 32);
        String sendTime = sdf.format(new Date());
        Map<String, Object> map = new HashMap<>();
        map.put("msgId", msgId);
        map.put("sendTime", sendTime);
        map.put("msg", msg);
        return map;
    }
}