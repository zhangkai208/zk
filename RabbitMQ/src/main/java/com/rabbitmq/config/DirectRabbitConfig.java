package com.rabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.amqp.core.Queue;

@Configuration
public class DirectRabbitConfig {
    @Bean
    public Queue queue() {
        return new Queue(RabbitMQConfig.TOPIC, true, false, false);
    }
    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(RabbitMQConfig.EXCHANGE_NAME, true, false);
    }
    @Bean
    public Binding binding() {
        return BindingBuilder
                .bind(queue())
                .to(exchange())
                .with(RabbitMQConfig.ROUTING_KEY);
    }
}
