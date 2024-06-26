package com.hello.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Slf4j
@Aspect
public class AspectV2 {

    // pointcut signature(= allOrder() / 메소드 이름과 파라미터를 합친 것)
    // 자바 클래스와 마찬가지로, 다른 애스펙트에서 참고하려면 public으로. -> 포인트컷만 모아놓은 애스펙트를 만들 수 있다.
    @Pointcut("execution(* com.hello.aop.order..*(..))")
    private void allOrder(){}

    @Around("allOrder()")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("[log] {}", joinPoint.getSignature()); // joinPoint 시그니처
        return joinPoint.proceed();
    }
}
