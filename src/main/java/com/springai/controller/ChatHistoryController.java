package com.springai.controller;

import com.springai.entity.ChatConversation;
import com.springai.entity.ChatMessage;
import com.springai.service.ChatHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ChatHistoryController {

    private final ChatHistoryService chatHistoryService;

    /**
     * 获取所有会话列表
     */
    @GetMapping("/conversations")
    public List<ChatConversation> getAllConversations() {
        return chatHistoryService.getAllConversations();
    }

    /**
     * 创建新会话
     */
    @PostMapping("/conversations")
    public ChatConversation createConversation(@RequestBody Map<String, String> body) {
        String title = body.getOrDefault("title", "新对话");
        return chatHistoryService.createConversation(title);
    }

    /**
     * 删除会话
     */
    @DeleteMapping("/conversations/{id}")
    public Map<String, Object> deleteConversation(@PathVariable Long id) {
        chatHistoryService.deleteConversation(id);
        return Map.of("success", true);
    }

    /**
     * 获取会话的所有消息
     */
    @GetMapping("/conversations/{id}/messages")
    public List<ChatMessage> getMessages(@PathVariable Long id) {
        return chatHistoryService.getMessagesByConversationId(id);
    }

    /**
     * 更新会话标题
     */
    @PutMapping("/conversations/{id}")
    public Map<String, Object> updateConversation(@PathVariable Long id, @RequestBody Map<String, String> body) {
        String title = body.get("title");
        if (title != null) {
            chatHistoryService.updateConversationTitle(id, title);
        }
        return Map.of("success", true);
    }
}
