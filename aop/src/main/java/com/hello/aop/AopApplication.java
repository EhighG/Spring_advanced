package com.hello.aop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AopApplication { // Web 의존성을 추가하지 않으면, 톰캣과 같은 WAS도 없고, 실행하자마자 종료됨

	public static void main(String[] args) {
		SpringApplication.run(AopApplication.class, args);
	}

}
