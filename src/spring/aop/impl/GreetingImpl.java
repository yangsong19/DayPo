package spring.aop.impl;

import org.springframework.stereotype.Component;

import spring.aop.face.Greeting;

@Component("greetingImpl")
public class GreetingImpl implements Greeting{
	
	public GreetingImpl() {
		super();
	}

	public void greeting(String what) {
		System.out.println(what);
//		throw new RuntimeException("Error");
	}

	private void goodMorning(String name) {
		System.out.println("Goodmorning, " + name);
	}
	
	private void goodNight(String name) {
		System.out.println("Goodnight, " + name);
	}
}
