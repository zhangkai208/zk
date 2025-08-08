package com.itheima.mp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.mp.domain.po.Addressdomain.po;
import com.itheima.mp.service.AddressService;
import com.itheima.mp.mapper.AddressMapper;
import org.springframework.stereotype.Service;

/**
* @author 张恺
* @description 针对表【address】的数据库操作Service实现
* @createDate 2025-07-30 14:22:13
*/
@Service
public class AddressServiceImpl extends ServiceImpl<AddressMapper, po>
    implements AddressService{

}




