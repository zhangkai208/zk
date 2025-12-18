package com.springai.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
@RestController
@RequestMapping("/ai")
public class ChatController {

    private final ChatClient chatClient;

    @RequestMapping(value = "/chat", produces = "text/event-stream;charset=utf-8")
    public Flux<String> chat(@RequestParam String message) {
        return chatClient.prompt()
                .user(message)  // 使用用户传入的message
                .stream()
                .content();
    }
}
