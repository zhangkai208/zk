package com.example.jwtdome.interceptor;

import com.example.jwtdome.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Map;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

public class JwtInterceptor implements HandlerInterceptor {

    private static final ThreadLocal<String> CURRENT_TOKEN = new ThreadLocal<>();

    private final StringRedisTemplate stringRedisTemplate;

    public JwtInterceptor(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authorization = request.getHeader("Authorization");
        if (!StringUtils.hasText(authorization)) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }

        String token = authorization.startsWith("Bearer ") ? authorization.substring(7) : authorization;

        Map<String, Object> userClaims;
        try {
            userClaims = JwtUtil.parseToken(token);
        } catch (Exception ex) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }

        Object userIdObj = userClaims.get("userId");
        if (userIdObj == null) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }

        String redisKey = "login:token:" + userIdObj;
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        String redisToken = ops.get(redisKey);
        if (!StringUtils.hasText(redisToken) || !token.equals(redisToken)) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }

        CURRENT_TOKEN.set(token);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        CURRENT_TOKEN.remove();
    }
}
