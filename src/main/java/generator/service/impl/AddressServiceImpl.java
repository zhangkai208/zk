package generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import generator.domain.Addressdomain;
import generator.service.AddressService;
import generator.mapper.AddressMapper;
import org.springframework.stereotype.Service;

/**
* @author 张恺
* @description 针对表【address】的数据库操作Service实现
* @createDate 2025-07-30 14:18:56
*/
@Service
public class AddressServiceImpl extends ServiceImpl<AddressMapper, Addressdomain>
    implements AddressService{

}




