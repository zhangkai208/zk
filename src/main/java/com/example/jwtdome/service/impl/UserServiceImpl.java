package com.example.jwtdome.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.jwtdome.service.UserService;
import com.example.jwtdome.domain.User;
import com.example.jwtdome.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author 张恺
* @description 针对表【user(用户表)】的数据库操作Service实现
* @createDate 2025-08-08 10:58:34
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {

}




