package com.springai.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.springai.entity.ChatConversation;
import com.springai.entity.ChatMessage;
import com.springai.mapper.ChatConversationMapper;
import com.springai.mapper.ChatMessageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatHistoryService {

    private final ChatConversationMapper conversationMapper;
    private final ChatMessageMapper messageMapper;

    /**
     * 获取所有会话列表（按更新时间倒序）
     */
    public List<ChatConversation> getAllConversations() {
        return conversationMapper.selectList(
                new LambdaQueryWrapper<ChatConversation>()
                        .orderByDesc(ChatConversation::getUpdatedAt)
        );
    }

    /**
     * 创建新会话
     */
    public ChatConversation createConversation(String title) {
        ChatConversation conversation = new ChatConversation();
        conversation.setTitle(title);
        conversation.setCreatedAt(LocalDateTime.now());
        conversation.setUpdatedAt(LocalDateTime.now());
        conversationMapper.insert(conversation);
        return conversation;
    }

    /**
     * 删除会话（消息会级联删除）
     */
    @Transactional
    public void deleteConversation(Long conversationId) {
        // 先删除消息
        messageMapper.delete(
                new LambdaQueryWrapper<ChatMessage>()
                        .eq(ChatMessage::getConversationId, conversationId)
        );
        // 再删除会话
        conversationMapper.deleteById(conversationId);
    }

    /**
     * 获取会话的所有消息
     */
    public List<ChatMessage> getMessagesByConversationId(Long conversationId) {
        return messageMapper.selectList(
                new LambdaQueryWrapper<ChatMessage>()
                        .eq(ChatMessage::getConversationId, conversationId)
                        .orderByAsc(ChatMessage::getCreatedAt)
        );
    }

    /**
     * 保存消息
     */
    public ChatMessage saveMessage(Long conversationId, String role, String content) {
        ChatMessage message = new ChatMessage();
        message.setConversationId(conversationId);
        message.setRole(role);
        message.setContent(content);
        message.setCreatedAt(LocalDateTime.now());
        messageMapper.insert(message);

        // 更新会话的更新时间
        ChatConversation conversation = conversationMapper.selectById(conversationId);
        if (conversation != null) {
            conversation.setUpdatedAt(LocalDateTime.now());
            // 如果是第一条消息，更新标题
            if ("user".equals(role) && "新对话".equals(conversation.getTitle())) {
                String newTitle = content.length() > 20 ? content.substring(0, 20) + "..." : content;
                conversation.setTitle(newTitle);
            }
            conversationMapper.updateById(conversation);
        }

        return message;
    }

    /**
     * 更新会话标题
     */
    public void updateConversationTitle(Long conversationId, String title) {
        ChatConversation conversation = conversationMapper.selectById(conversationId);
        if (conversation != null) {
            conversation.setTitle(title);
            conversation.setUpdatedAt(LocalDateTime.now());
            conversationMapper.updateById(conversation);
        }
    }
}
