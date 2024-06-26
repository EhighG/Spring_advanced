package com.hello.aop.member.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME) // 런타임까지 해당 어노테이션이 유지된다. 다른 값을 선택하면 (현재 방식인) 동적 프록시 생성이 불가능하다.
public @interface ClassAop {
}
