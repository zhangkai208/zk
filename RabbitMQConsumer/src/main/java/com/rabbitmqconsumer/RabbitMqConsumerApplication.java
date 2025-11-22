package com.rabbitmqconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RabbitMqConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(RabbitMqConsumerApplication.class, args);
    }

    @org.springframework.context.annotation.Bean
    public org.springframework.amqp.support.converter.MessageConverter messageConverter() {
        org.springframework.amqp.support.converter.SimpleMessageConverter converter = new org.springframework.amqp.support.converter.SimpleMessageConverter();
        converter.setAllowedListPatterns(java.util.List.of("java.util.*", "java.lang.*"));
        return converter;
    }

}
