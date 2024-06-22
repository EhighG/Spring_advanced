package hello.proxy.proxyfactory;

import hello.proxy.common.advice.TimeAdvice;
import hello.proxy.common.service.ConcreteService;
import hello.proxy.common.service.ServiceImpl;
import hello.proxy.common.service.ServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.AopUtils;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class ProxyFactoryTest {

    @Test
    @DisplayName("타겟이 인터페이스 기반이면, JDK 동적 프록시 사용")
    void interfaceProxy() {
        ServiceInterface target = new ServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target); // 프록시 생성 시 이 정보를 기반으로 생성
        proxyFactory.addAdvice(new TimeAdvice()); // 프록시로 추가하고자 하는 기능 추가

        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();
        log.info("targetClass = {}", target.getClass());
        log.info("proxyClass = {}", proxy.getClass()); // class jdk.proxy3.$Proxy13

        proxy.save(); // TimeProxy(in TimeAdvice) 실행됨

        // ProxyFactory가 제공하는 기능들 (ProxyFactory로 생성한 프록시에만 사용 가능)
        assertThat(AopUtils.isAopProxy(proxy)).isTrue();
        assertThat(AopUtils.isJdkDynamicProxy(proxy)).isTrue();
        assertThat(AopUtils.isCglibProxy(proxy)).isFalse();
    }

    @Test
    @DisplayName("구체 클래스만 있으면, CGLIB 사용")
    void concreteProxy() {
//        ServiceInterface target = new ServiceImpl();
        ConcreteService target = new ConcreteService();

        ProxyFactory proxyFactory = new ProxyFactory(target); // 프록시 생성 시 이 정보를 기반으로 생성
        proxyFactory.addAdvice(new TimeAdvice()); // 프록시로 추가하고자 하는 기능 추가

        ConcreteService proxy = (ConcreteService) proxyFactory.getProxy();
        log.info("targetClass = {}", target.getClass());
        log.info("proxyClass = {}", proxy.getClass()); // class hello.proxy.common.service.ConcreteService$$EnhancerBySpringCGLIB$$73081182

        proxy.call(); // TimeProxy(in TimeAdvice) 실행됨

        // ProxyFactory가 제공하는 기능들 (ProxyFactory로 생성한 프록시에만 사용 가능)
        assertThat(AopUtils.isAopProxy(proxy)).isTrue();
        assertThat(AopUtils.isJdkDynamicProxy(proxy)).isFalse();
        assertThat(AopUtils.isCglibProxy(proxy)).isTrue();
    }

    @Test
    @DisplayName("ProxyTargetClass 옵션을 사용하면 인터페이스가 있어도 CGLIB을 사용하고, 클래스 기반 프록시 사용")
    void proxyTargetClass() {
        ServiceInterface target = new ServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target); // 프록시 생성 시 이 정보를 기반으로 생성

        proxyFactory.setProxyTargetClass(true); // 중요하다! 실무에서도 종종 등장한다.

        proxyFactory.addAdvice(new TimeAdvice()); // 프록시로 추가하고자 하는 기능 추가

        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();
        log.info("targetClass = {}", target.getClass());
        log.info("proxyClass = {}", proxy.getClass()); // class hello.proxy.common.service.ServiceImpl$$EnhancerBySpringCGLIB$$f476ff43

        proxy.save(); // TimeProxy(in TimeAdvice) 실행됨

        // ProxyFactory가 제공하는 기능들 (ProxyFactory로 생성한 프록시에만 사용 가능)
        assertThat(AopUtils.isAopProxy(proxy)).isTrue();
        assertThat(AopUtils.isJdkDynamicProxy(proxy)).isFalse(); // pass
        assertThat(AopUtils.isCglibProxy(proxy)).isTrue(); // pass
    }
}
