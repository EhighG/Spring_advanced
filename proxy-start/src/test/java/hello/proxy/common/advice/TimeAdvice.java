package hello.proxy.common.advice;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

@Slf4j
public class TimeAdvice implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        log.info("TimeProxy 실행");
        long startTime = System.currentTimeMillis();
        // 적용 대상 로직 실행
//        Object result = method.invoke(target, args);
        Object result = invocation.proceed(); // 알아서 target을 찾고, 알맞게 인자도 넣어서 호출하고, 결과를 받는다.

        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("TimeProxy 종료 / resultTime = {}", resultTime);
        return result;
    }
}
