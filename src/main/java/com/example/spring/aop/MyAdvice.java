package com.example.spring.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Aspect
public class MyAdvice {

    /*@Pointcut("execution(* *.update(..))")
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

    }*/

    @Pointcut("execution(* com.example.spring.dao.*.findName(..))")
    private  void  pt(){

    }


   /* @Before("pt()")
    public void method(){
        System.out.println(System.currentTimeMillis());
    }*/
    /*@Before("pt()")
    public void before(JoinPoint pjp){
        Object[] args = pjp.getArgs();
        System.out.println(Arrays.toString(args));
        System.out.println("before");
    }

    @After("pt()")
    public void after(JoinPoint pjp){
        Object[] args = pjp.getArgs();
        System.out.println(Arrays.toString(args));
        System.out.println("after");
    }*/

    @Around("pt()")
    public Object  around(ProceedingJoinPoint pjp) {
        Object[] args = pjp.getArgs();
        System.out.println(Arrays.toString(args));
        Object proceed = null;
        try {
            proceed = pjp.proceed(args);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        return proceed;
    }
    /*@Around("pt() || pt4()")
    public void   around(ProceedingJoinPoint pjp) throws Throwable {
        Long start = System.currentTimeMillis();
        System.out.println("around before");
        for (int i = 0; i < 10; i++) {
            Object proceed = pjp.proceed();

        }
        Long end = System.currentTimeMillis();
        System.out.println("around after");
        System.out.println("执行时间：" + (end - start));
    }*/
    @AfterReturning(value = "pt()", returning = "obj")
    public void  afterReturning(Object obj){
        System.out.println("afterReturning"+obj);
    }
    @AfterThrowing(value = "pt()", throwing = "e")
    public void  afterThrowing(Throwable e){
        System.out.println("afterThrowing");
    }
}
