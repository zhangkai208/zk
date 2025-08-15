package com.example.spring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class MyAdvice {

    @Pointcut("execution(* *.update(..))")
    private  void  pt(){

    }
    @Pointcut("execution(* *.select(..))")
    private  void  pt2(){

    }

   /* @Before("pt()")
    public void method(){
        System.out.println(System.currentTimeMillis());
    }*/
    /*@Before("pt()")
    public void before(){
        System.out.println("before");
    }

    @After("pt()")
    public void after(){
        System.out.println("after");
    }*/
    @Around("pt2()")
    public Object  around(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("around before");
        Object proceed = pjp.proceed();
        System.out.println("around after");
        return  proceed;
    }
    @AfterReturning("pt2()")
    public void  afterReturning(){
        System.out.println("afterReturning");
    }
    @AfterThrowing("pt2()")
    public void  afterThrowing(){
        System.out.println("afterThrowing");
    }
}
