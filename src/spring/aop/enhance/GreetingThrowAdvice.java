package spring.aop.enhance;

import java.lang.reflect.Method;

import org.springframework.aop.ThrowsAdvice;
import org.springframework.stereotype.Component;

@Component("throw")
public class GreetingThrowAdvice implements ThrowsAdvice {

	public void afterThrowing(Method method, Object[] args, Object target,
			Exception ex) {
		System.out.println("---------- Throw Exception ----------");
		System.out.println("Target Class: " + target.getClass().getName());
		System.out.println("Method Name: " + method.getName());
		System.out.println("Exception Message: " + ex.getMessage());
		System.out.println("-------------------------------------");
	}

}
