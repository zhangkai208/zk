package com.example.jwtdome.controller;

import com.example.jwtdome.util.JwtUtil;
import java.util.HashMap;
import java.util.Map;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final StringRedisTemplate stringRedisTemplate;

    public AuthController(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    /**
     * 简单登录：传入 userId，签发 token 并写入 Redis。
     * 请求体示例：{"userId": 1}
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, Object> body) {
        Object userIdObj = body.get("userId");
        if (userIdObj == null) {
            return ResponseEntity.badRequest().build();
        }
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userIdObj);
        String token = JwtUtil.createToken(claims);
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        ops.set("login:token:" + userIdObj, token);

        Map<String, Object> resp = new HashMap<>();
        resp.put("token", token);
        return ResponseEntity.ok(resp);
    }
}


