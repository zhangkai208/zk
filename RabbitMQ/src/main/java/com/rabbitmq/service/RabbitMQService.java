package com.rabbitmq.service;

import java.util.Map;

public interface RabbitMQService {
    public String sendMsg(String msg) throws Exception;
    public String sendFanoutMsg(String msg) throws Exception;
    public String sendTopicMsg(String msg, String routingKey) throws Exception;
    public String sendHeadersMsg(String msg, Map<String, Object> headers) throws Exception;
}
