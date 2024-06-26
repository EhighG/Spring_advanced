package com.hello.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Slf4j
@Aspect
public class AspectV3 {

    // pointcut signature(= allOrder() / 메소드 이름과 파라미터를 합친 것)
    @Pointcut("execution(* com.hello.aop.order..*(..))")
    private void allOrder(){}

    // 타입 이름 패턴이 @Service인 것(트랜잭션 구현을 위해) (인터페이스에도 적용된다.)
    @Pointcut("execution(* *..*Service.*(..))")
    private void allService(){}

    @Around("allOrder()")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("[log] {}", joinPoint.getSignature()); // joinPoint 시그니처
        return joinPoint.proceed();
    }

    // com.hello.order 패키지와 하위 패키지이면서, 타입 이름 패턴이 *Service
    @Around("allOrder() && allService()") // 포인트컷 시그니처 = 표현식을 변수화
    public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable {

        try {
            log.info("[트랜잭션 시작] {}", joinPoint.getSignature());
            // 정상 종료 시, 커밋
            Object result = joinPoint.proceed();
            log.info("[트랜잭션 커밋] {}", joinPoint.getSignature());
            return result;
        } catch (Exception e) {
            log.info("[트랜잭션 롤백] {}", joinPoint.getSignature());
            throw e;
        } finally {
            log.info("[리소스 릴리즈] {}", joinPoint.getSignature());
        }
    }
}
