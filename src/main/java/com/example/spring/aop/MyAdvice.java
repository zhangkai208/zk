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
    @Pointcut("execution(* *.save(..))")
    private  void  pt3(){

    }
    @Pointcut("execution(* *.delete(..))")
    private  void  pt4(){

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
    @Around("pt() || pt4()")
    public void   around(ProceedingJoinPoint pjp) throws Throwable {
        Long start = System.currentTimeMillis();
        System.out.println("around before");
        for (int i = 0; i < 10; i++) {
            Object proceed = pjp.proceed();

        }
        Long end = System.currentTimeMillis();
        System.out.println("around after");
        System.out.println("执行时间：" + (end - start));
    }
    /*@AfterReturning("pt2()")
    public void  afterReturning(){
        System.out.println("afterReturning");
    }
    @AfterThrowing("pt2()")
    public void  afterThrowing(){
        System.out.println("afterThrowing");
    }*/
}
