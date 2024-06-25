package hello.proxy.config.v6_aop.aspect;

import hello.proxy.trace.TraceStatus;
import hello.proxy.trace.logtrace.LogTrace;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import java.lang.reflect.Method;

@Slf4j
@Aspect // advisor(=advice + pointcut)을 편리하게 생성
public class LogTraceAspect {

    private final LogTrace logTrace;


    public LogTraceAspect(LogTrace logTrace) {
        this.logTrace = logTrace;
    }

    // 생성되는 advisor 수는 @Aspect가 아니라 @Arount 붙은 메소드 수를 따라간다.
    @Around("execution(* hello.proxy.app..*(..))") // pointcut
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable { // advice 로직
        TraceStatus status = null;
        try {
            String message = joinPoint.getSignature().toShortString();
            status = logTrace.begin(message);

            // target 호출
            Object result = joinPoint.proceed(); // target 호출 후 결과 반환
            logTrace.end(status);
            return result;
        } catch (Exception e) {
            logTrace.exception(status, e);
            throw e;
        }
    }
}
