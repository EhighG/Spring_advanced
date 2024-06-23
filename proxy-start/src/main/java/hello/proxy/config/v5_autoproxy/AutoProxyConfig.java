package hello.proxy.config.v5_autoproxy;

import hello.proxy.config.AppV1Config;
import hello.proxy.config.AppV2Config;
import hello.proxy.config.v3_proxyfactory.advice.LogTraceAdvice;
import hello.proxy.trace.logtrace.LogTrace;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Slf4j
@Configuration
@Import({AppV1Config.class, AppV2Config.class})
public class AutoProxyConfig {

//    @Bean // advisor1, 2 모두 빈으로 등록하면, 한 ThreadLocal 안에서 두 개 모두 작동하므로 동시성 이슈 때처럼 동작한다.
    public Advisor advisor1(LogTrace logTrace) {
        // pointcut ('no-log' 메소드는 제외하기 위함)
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedNames("request*", "order*", "save*");
        // advice
        LogTraceAdvice advice = new LogTraceAdvice(logTrace);
        // pointcut을 기반으로 빈 클래스마다 모든 메소드를 검사해서 한 메소드라도 대상인 클래스는, 프록시 객체를 대신 등록한다.
        return new DefaultPointcutAdvisor(pointcut, advice);
    }

//    @Bean
    public Advisor advisor2(LogTrace logTrace) {
        // pointcut
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* hello.proxy.app..*(..))"); // 모든 반환 타입, app 하위 패키지, 모든 메소드 이름, 파라미터는 상관없이
        // advice
        LogTraceAdvice advice = new LogTraceAdvice(logTrace);
        //advisor
        return new DefaultPointcutAdvisor(pointcut, advice);
    }

    @Bean
    public Advisor advisor3(LogTrace logTrace) {
        // pointcut
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* hello.proxy.app..*(..)) && !execution(* hello.proxy.app..noLog(..))");
        // advice
        LogTraceAdvice advice = new LogTraceAdvice(logTrace);
        //advisor
        return new DefaultPointcutAdvisor(pointcut, advice);
    }
}
