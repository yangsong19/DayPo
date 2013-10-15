package spring.aop.impl;

import org.springframework.stereotype.Component;

import spring.aop.face.Greeting;
import spring.aop.face.Tag;

@Component("greetingImpl")
public class GreetingImpl implements Greeting{
	
	public GreetingImpl() {
		super();
	}
	
	//以上示例中只有一个方法，如果有多个方法，我们只想拦截其中某些时，这种解决方案会更加有价值
//	@Tag
	public void greeting(String what) {
		System.out.println("Hello, " + what);
//		throw new RuntimeException("Error");
	}

	@Tag
	public void goodMorning(String name) {
		System.out.println("Goodmorning, " + name);
	}
	
//	@Tag
	public void goodNight(String name) {
		System.out.println("Goodnight, " + name);
	}
}
