package com.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DirectRabbitConfig {
    
    @Bean
    public Queue queue() {
        return new Queue(RabbitMQConfig.TOPIC, true, false, false);
    }
    
    @Bean
    public Queue queue1(){
        return new Queue(RabbitMQConfig.QUEUE_NAME1, true, false, false);
    }
    
    @Bean
    public Queue queue2(){
        return new Queue(RabbitMQConfig.QUEUE_NAME2, true, false, false);
    }
    
    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(RabbitMQConfig.EXCHANGE_NAME, true, false);
    }
    
    @Bean
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange(RabbitMQConfig.FANOUT_EXCHANGE_NAME, true, false);
    }
    @Bean
    public TopicExchange topicExchange(){
        return new TopicExchange(RabbitMQConfig.TOPIC_EXCHANGE_NAME, true, false);
    }

    @Bean
    public Binding binding() {
        return BindingBuilder
                .bind(queue())
                .to(exchange())
                .with(RabbitMQConfig.ROUTING_KEY);
    }
    
    @Bean
    public Binding binding1(){
        return BindingBuilder.bind(queue1()).to(topicExchange())
                .with("a.*");
    }
    
    @Bean
    public Binding binding2(){
        return BindingBuilder.bind(queue2()).to(topicExchange())
                .with("b.*");
    }
    
    // Headers Exchange 配置
    @Bean
    public HeadersExchange headersExchange(){
        return new HeadersExchange(RabbitMQConfig.HEADERS_EXCHANGE_NAME, true, false);
    }
    
    // queue1 绑定到 HeadersExchange，匹配规则：x-match=all，需要所有header都匹配
    @Bean
    public Binding headersBinding1(){
        return BindingBuilder.bind(queue1())
                .to(headersExchange())
                .whereAll("type", "format").exist();  // type 和 format 都必须存在
    }
    
    // queue2 绑定到 HeadersExchange，匹配规则：x-match=any，任意header匹配即可
    @Bean
    public Binding headersBinding2(){
        return BindingBuilder.bind(queue2())
                .to(headersExchange())
                .whereAny("priority").exist();  // 只要有 priority 就匹配
    }
}
