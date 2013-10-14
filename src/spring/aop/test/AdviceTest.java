package spring.aop.test;

import java.util.Arrays;

import org.springframework.aop.framework.ProxyFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import spring.aop.enhance.GreetingAfterAdvice;
import spring.aop.enhance.GreetingAroundAdvice;
import spring.aop.enhance.GreetingBeforeAdvice;
import spring.aop.enhance.GreetingBeforeAndAfterAdvice;
import spring.aop.face.Apology;
import spring.aop.face.Greeting;
import spring.aop.impl.GreetingImpl;

public class AdviceTest {
	public static void main(String[] args) {
		
		//org.springframework.aop.framework.Cglib2AopProxy
		ProxyFactory proxyFactory = new ProxyFactory();
		GreetingImpl target = new GreetingImpl();
		proxyFactory.setTarget(target);
		GreetingBeforeAdvice before = new GreetingBeforeAdvice();
		proxyFactory.addAdvice(before);
		GreetingAfterAdvice after = new GreetingAfterAdvice();
		proxyFactory.addAdvice(after);
		
		Greeting greeting = (Greeting) proxyFactory.getProxy();
		greeting.greeting("Is this spring 'before or after advice' of aop?");
		
		System.out.println("\n------------BeforeAndAfterAdvice--------------------\n");
		
		System.out.println("index of before is: " + proxyFactory.indexOf(before));
		System.out.println("index of after is: " + proxyFactory.indexOf(after));
//		proxyFactory = new ProxyFactory();
		proxyFactory.removeAdvice(before);
		proxyFactory.removeAdvice(after);
//		proxyFactory.setTarget(new IntroduceQuestionImpl());
		GreetingBeforeAndAfterAdvice ba = new GreetingBeforeAndAfterAdvice();
		proxyFactory.addAdvice(ba);
		greeting = (Greeting) proxyFactory.getProxy();
		greeting.greeting("Is this spring 'before and after advice' of aop?");
		
		System.out.println("\n--------------AroundAdvice------------------\n");
		//环绕增强
		proxyFactory.removeAdvice(ba);
		proxyFactory.addAdvice(new GreetingAroundAdvice());
		greeting = (Greeting) proxyFactory.getProxy();
		greeting.greeting("Is this spring 'around advice' of aop?");
		
		System.out.println("\n--------------Replace With Configuration---ThrowAdvice-------------\n");
		//xml annotation configuration spring aop 
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-aop.xml"); 
		greeting = (Greeting) context.getBean("proxy");//is equivalent to ProxyFactory
		greeting.greeting("Is this spring annotation xml configuration of aop?");
		
		System.out.println("\n<------IntroduceAdvice-------\n");//注意:转型为目标类,而并非它的 Greeting 接口
		context = new ClassPathXmlApplicationContext("spring-aop-introduce.xml"); 
		greeting = (Greeting) context.getBean("proxy");
		greeting.greeting("This is invoking the target class 'GreetingImpl' method");
		
		System.out.println("\n-------suprised------>\n");
		// 将目标类强制向上转型为 Apology 接口,这是引入增强给我们带来的特性,也就是【接口动态实现】功能 
	    Apology apology = (Apology) greeting;
	    apology.saySorry("I'm very excited that everything is OK!");
		
	    System.out.println("\n------Advisor--Pointcut------\n");
	    context = new ClassPathXmlApplicationContext("spring-advisor.xml");
	    target = (GreetingImpl) context.getBean("proxy");
	    target.goodMorning("Lucy");
	    target.goodNight("Lily");
	    
	    System.out.println("\n----AutoProxy------\n");
	    context = new ClassPathXmlApplicationContext("spring-autoproxy.xml");
	    System.out.println(Arrays.toString(context.getBeanDefinitionNames()));//这个方法很有用,参考价值
	    target = (GreetingImpl) context.getBean("greetingImpl");
	    target.goodMorning("Yangsong");
	    target.goodNight("Mico");
	    
	    System.out.println("\n----AspectJ------\n");
	    context = new ClassPathXmlApplicationContext("spring-aspectj.xml");
	    target = (GreetingImpl) context.getBean("greetingImpl");
	    target.greeting("Jemy");
	    target.goodMorning("Jack");
	    target.goodNight("Tom");
/*	    
 * 从 Spring ApplicationContext 中获取 greetingImpl 对象（其实是个代理对象），可转型为自己
 * 静态实现的接口 Greeting，也可转型为自己动态实现的接口 Apology，切换起来非常方便。使用
 * AspectJ 的引入增强比原来的 Spring AOP 的引入增强更加方便了，而且还可面向接口编程（
 * 以前只能面向实现类），这也算一个非常巨大的突破。
 * 
 *  */
	    apology = (Apology) target;
	    apology.saySorry("Tony");

	    
	    System.out.println("\n-------AspectJ-Config-----\n");
	    context = new ClassPathXmlApplicationContext("spring-aspectj-config.xml");
	    greeting = (Greeting) context.getBean("greetingImpl");
	    
	    apology = (Apology) greeting;
	    apology.saySorry("Termit");
	}
}
