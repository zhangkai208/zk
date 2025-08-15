package com.example.mybatis.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class ProjectAdvice {
    @Pointcut("execution(* com.example.mybatis.service.*.*(..))")
    public void servicept() {

    }

    @Around("servicept()")
    public void around(ProceedingJoinPoint joinPoint) throws Throwable {

        Signature signature = joinPoint.getSignature();
        Class declaringType = signature.getDeclaringType();
        String name = signature.getName();
        Long start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            joinPoint.proceed();
        }

        Long end = System.currentTimeMillis();

        System.out.println( declaringType + " 路径 " + "." + name + " 方法 " + "执行时间：" + (end - start) + "ms");

    }
}
