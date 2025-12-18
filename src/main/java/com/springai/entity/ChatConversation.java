package com.springai.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 聊天会话实体类
 * 用于存储聊天会话的相关信息
 */
@Data
@TableName("chat_conversation")
public class ChatConversation {
    
    /**
     * 主键ID
     * 使用数据库自增方式生成
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    
    /**
     * 会话标题
     * 用于标识会话内容
     */
    private String title;
    
    /**
     * 创建时间
     * 记录会话创建的具体时间
     */
    private LocalDateTime createdAt;
    
    /**
     * 更新时间
     * 记录会话最后一次更新的时间
     */
    private LocalDateTime updatedAt;
}
