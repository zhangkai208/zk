package com.example.jwtdome.mapper;

import com.example.jwtdome.domain.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 张恺
* @description 针对表【user(用户表)】的数据库操作Mapper
* @createDate 2025-08-08 10:58:34
* @Entity jwtdome.domain.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

}




