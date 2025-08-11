package com.example.jwtdome.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Map;

public class JwtUtil {
    //生成token
    private static final String KEY = "zk";
    public static String createToken(Map<String,Object> claims){
    return JWT.create()
            .withClaim("user",claims)
            .sign(Algorithm.HMAC256(KEY));
    }
    //解析token
    public static Map<String,Object> parseToken(String token){
        return JWT.require(Algorithm.HMAC256(KEY))
                .build()
                .verify(token)
                .getClaim("user")
                .asMap();
    }
}
