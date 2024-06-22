package hello.proxy.advisor;

import hello.proxy.common.advice.TimeAdvice;
import hello.proxy.common.service.ServiceImpl;
import hello.proxy.common.service.ServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;

@Slf4j
public class AdvisorTest {

    @Test
    void advisorTest() {
        ServiceInterface target = new ServiceImpl();
        // 어떤 타겟에 프록시를 적용할지를 설정
        ProxyFactory proxyFactory = new ProxyFactory(target);
        // advisor 생성, 적용
        /* 어느 로직을 어디에 적용할지를 설정
        Pointcut.TRUE = 항상 참을 반환하는 Pointcut.
        아래는 포인트컷 없이 addAdvice(advice) 만 했을 때 결과적으로 실행되는 코드이다.
        ProxyFactory는 어드바이저가 필수이다.
         */
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(Pointcut.TRUE, new TimeAdvice());
        proxyFactory.addAdvisor(advisor);
        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

        proxy.save();
        proxy.find();
    }
}
