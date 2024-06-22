package hello.proxy.cglib.code;


import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

@Slf4j
public class TimeMethodInterceptor implements MethodInterceptor { // MethodInterceptor는 Callback의 하위 타입이다.

    private final Object target;

    public TimeMethodInterceptor(Object target) {
        this.target = target;
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        log.info("TimeProxy 실행");
        long startTime = System.currentTimeMillis();

        // 적용 대상 로직 실행
        Object result = methodProxy.invoke(target, args);// CGLIB에선 성능 상 methodProxy를 쓰는 방식을 권장한다.

        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("TimeProxy 종료 / resultTime = {}", resultTime);
        return result;
    }
}
