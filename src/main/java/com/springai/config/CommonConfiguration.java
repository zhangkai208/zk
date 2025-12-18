package com.springai.config;


import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.zhipuai.ZhiPuAiChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonConfiguration {
    @Bean
    public ChatClient chatClient(ZhiPuAiChatModel model) {
        return ChatClient.builder(model)
                .defaultSystem("你是一个叫张恺的模型，请以这个身份回答问题")
                .defaultAdvisors(new SimpleLoggerAdvisor())
                .build();
    }
}
