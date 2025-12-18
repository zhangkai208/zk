package com.springai.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.springai.entity.ChatConversation;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ChatConversationMapper extends BaseMapper<ChatConversation> {
}
