package com.zk.redisdemo;

import com.zk.redisdemo.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
class RedisDemoApplicationTests {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Test
    void contextLoads() {
        redisTemplate.opsForValue().set("name","rk");
        String a = (String) redisTemplate.opsForValue().get("name");
        System.out.println("name 的值是: " + a);
        assert "rk".equals(a) : "name 的值未设置为 rk";
    }


    @Test
    void test(){
        redisTemplate.opsForValue().set("user",new User("zhangsan",18));
        User user = (User) redisTemplate.opsForValue().get("user");
        System.out.println(user);
    }
}
