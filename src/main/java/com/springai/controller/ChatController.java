package com.springai.controller;

import com.springai.entity.ChatMessage;
import com.springai.service.ChatHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
@RestController
@RequestMapping("/ai")
public class ChatController {

    private final ChatClient chatClient;
    private final ChatHistoryService chatHistoryService;

    @RequestMapping(value = "/chat", produces = "text/event-stream;charset=utf-8")
    public Flux<String> chat(
            @RequestParam String message,
            @RequestParam Long conversationId) {
        
        // 保存用户消息到数据库
        chatHistoryService.saveMessage(conversationId, "user", message);

        // 用于收集AI回复
        StringBuilder aiResponse = new StringBuilder();

        return chatClient.prompt()
                .user(message)
                .advisors(advisor -> advisor
                        .param(ChatMemory.CONVERSATION_ID, String.valueOf(conversationId)))
                .stream()
                .content()
                .doOnNext(chunk -> {
                    // 收集AI回复内容
                    aiResponse.append(chunk);
                })
                .doOnComplete(() -> {
                    // 流结束后保存AI回复到数据库
                    if (aiResponse.length() > 0) {
                        chatHistoryService.saveMessage(conversationId, "assistant", aiResponse.toString());
                    }
                });
    }
}
