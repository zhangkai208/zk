package com.springai.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 聊天消息实体类
 * 用于存储聊天消息的相关信息
 */
@Data
@TableName("chat_message")
public class ChatMessage {
    
    /**
     * 消息ID
     * 使用数据库自增策略生成
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    
    /**
     * 对话ID
     * 用于关联同一条对话中的多条消息
     */
    private Long conversationId;
    
    /**
     * 消息角色
     * 区分为用户(user)或助手(assistant)
     */
    private String role;  // user / assistant
    
    /**
     * 消息内容
     * 存储实际的对话文本
     */
    private String content;
    
    /**
     * 创建时间
     * 记录消息的创建时间
     */
    private LocalDateTime createdAt;
}
