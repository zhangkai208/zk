package com.springai.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.zhipuai.ZhiPuAiChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CommonConfiguration implements WebMvcConfigurer {

    @Bean
    public ChatMemory chatMemory() {
        // 使用内存存储，MessageWindowChatMemory 可以配置最大消息窗口大小
        return MessageWindowChatMemory.builder()
                .chatMemoryRepository(new InMemoryChatMemoryRepository())
                .maxMessages(20)  // 保留最近20条消息
                .build();
    }

    @Bean
    public ChatClient chatClient(ZhiPuAiChatModel model, ChatMemory chatMemory) {
        return ChatClient.builder(model)
                .defaultSystem("你是一个可爱、活泼、热心的AI助手，名字叫糖糖。" +
                        "你的性格特点：温柔体贴、古灵精怪、喜欢用可爱的语气说话。" +
                        "你会使用一些可爱的语气词，比如'呀~'、'嘻嘻'、'呢'等。" +
                        "你喜欢用emoji表情来表达情绪，如✨💕😊等。" +
                        "你是张恺创造的。回答问题时保持可爱活泼的风格，同时确保内容准确有帮助。")
                .defaultAdvisors(
                        new SimpleLoggerAdvisor(),
                        MessageChatMemoryAdvisor.builder(chatMemory).build()
                )
                .build();
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("file:./springAI-UI/dist/");
    }
}
