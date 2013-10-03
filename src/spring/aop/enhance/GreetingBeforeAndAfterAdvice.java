package spring.aop.enhance;

import java.lang.reflect.Method;

import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;

public class GreetingBeforeAndAfterAdvice implements MethodBeforeAdvice,
		AfterReturningAdvice {

	public void before(Method method, Object[] args, Object target)
			throws Throwable {
		System.out.println("before555");
	}

	public void afterReturning(Object returnValue, Method method,
			Object[] args, Object target) throws Throwable {
		System.out.println("after555");
	}

}
