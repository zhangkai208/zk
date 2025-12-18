package com.springai.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.springai.entity.ChatMessage;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChatMessageMapper extends BaseMapper<ChatMessage> {
    
    default List<ChatMessage> selectByConversationId(Long conversationId) {
        return selectList(new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<ChatMessage>()
                .eq(ChatMessage::getConversationId, conversationId)
                .orderByAsc(ChatMessage::getCreatedAt));
    }
}
