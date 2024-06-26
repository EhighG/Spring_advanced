package com.hello.aop.order.aop;

import org.aspectj.lang.annotation.Pointcut;

public class Pointcuts {

    // com.hello.aop.order 이하 패키지
    @Pointcut("execution(* com.hello.aop.order..*(..))")
    public void allOrder(){}

    // 타입 이름 패턴이 @Service인 것
    @Pointcut("execution(* *..*Service.*(..))")
    public void allService(){}

    // com.hello.aop.order 이하 패키지이면서, 타입 이름 패턴이 @Service인 것
    @Pointcut("allOrder() && allService()")
    public void orderAndService() {}
}
