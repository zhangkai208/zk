package com.zk.redisdemo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zk.redisdemo.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Map;

@SpringBootTest
public class RedisStringTests {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    void contextLoads() {
        stringRedisTemplate.opsForValue().set("name","ss");
        Object a =  stringRedisTemplate.opsForValue().get("name");
        System.out.println("name 的值是: " + a);

    }

private static final ObjectMapper objectMapper = new ObjectMapper();
    @Test
    void test() throws JsonProcessingException {
        User user = new User("zhangsan", 18);
        String json = objectMapper.writeValueAsString(user);
        stringRedisTemplate.opsForValue().set("user",json);
        String jsonuser = stringRedisTemplate.opsForValue().get("user");
        objectMapper.readValue(jsonuser, User.class);
        System.out.println(jsonuser);

    }

    @Test
    void test2() {
        stringRedisTemplate.opsForHash().put("userHash","name","zhangsan");
        stringRedisTemplate.opsForHash().put("userHash","age","18");
        Map<Object, Object> userHash = stringRedisTemplate.opsForHash().entries("userHash");
        System.out.println(userHash);
    }
}
