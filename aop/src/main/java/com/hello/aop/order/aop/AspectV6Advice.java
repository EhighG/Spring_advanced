package com.hello.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

import java.util.List;

@Slf4j
@Aspect
public class AspectV6Advice {

//    @Around("com.hello.aop.order.aop.Pointcuts.orderAndService()") // 포인트컷 시그니처 = 표현식을 변수화
//            // 활용: 실패 시 retry 등도 가능하다.
//    public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable { // 첫 번째 파라미터는 항상 고정
//        try {
//            // @Before : 실행 전에 실행
//            log.info("[트랜잭션 시작] {}", joinPoint.getSignature());
//            // 정상 종료 시, 커밋
//            Object result = joinPoint.proceed(); // 주의! 타겟을 호출하지 않으면, @Before~등이 모두 호출되지 않음.
//            // @AfterReturning : 정상 종료 휴 실행
//            log.info("[트랜잭션 커밋] {}", joinPoint.getSignature());
//            return result;
//        } catch (Exception e) {
//            // @AfterThrowing : 예외 발생 시 실행
//            log.info("[트랜잭션 롤백] {}", joinPoint.getSignature());
//            throw e;
//        } finally {
//            // @After : 정상 or 예외 관계없이 실행. 보통 리소스 해제 등에 사용
//            log.info("[리소스 릴리즈] {}", joinPoint.getSignature());
//        }
//    }

    // 세부 범위지정한 어드바이스들은, @Around보다 내부에서 실행된다.

    // ProceedingJoinPoint는 @Around에서만 쓸 수 있다.
    // JoinPoint는 안 받을수도 있다.
    @Before("com.hello.aop.order.aop.Pointcuts.orderAndService()")
    public void doBefore(JoinPoint joinPoint) {
        log.info("[before] {}", joinPoint.getSignature());
    }

    // result자리는 returning 옵션값과 매개변수명이 같아야 하고, 타깃 메소드의 반환타입과도 맞아야 함.(반환타입 다르면 실행 안됨)
    @AfterReturning(value = "com.hello.aop.order.aop.Pointcuts.orderAndService()", returning = "result")
    public void doReturn(JoinPoint joinPoint, Object result) {
        log.info("[return] {}, return = {}", joinPoint.getSignature(), result);
        // 반환값 변경은 불가능. (반환값이 참조 타입이면, 주소값이 같은 걸 이용해 변경은 가능
    }

    @AfterThrowing(value = "com.hello.aop.order.aop.Pointcuts.orderAndService()", throwing = "ex")
    public void doThrowing(JoinPoint joinPoint, Exception ex) {
        log.info("[ex] {}, message = {}", joinPoint.getSignature(), ex.getMessage());
    }

    @After(value = "com.hello.aop.order.aop.Pointcuts.orderAndService()")
    public void doAfter(JoinPoint joinPoint) {
        log.info("[after] {}", joinPoint.getSignature());
    }
}
