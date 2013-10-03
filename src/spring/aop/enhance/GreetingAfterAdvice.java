package spring.aop.enhance;

import java.lang.reflect.Method;

import org.springframework.aop.AfterReturningAdvice;

public class GreetingAfterAdvice implements AfterReturningAdvice{

	public void afterReturning(Object result, Method method, Object[] args,
			Object target) throws Throwable {
		System.out.println("after444");
	}

}
