package spring.aop.test;

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
		
		System.out.println("\n<------IntroduceAdvice-------\n");//引入增强
		context = new ClassPathXmlApplicationContext("spring-aop-introduce.xml"); 
		target = (GreetingImpl) context.getBean("proxy");// �ӿ�
		target.greeting("This is invoking the target class 'GreetingImpl' method");
		
		System.out.println("\n-------suprised------>\n");
		//本来Apology接口和GreetingImpl毫无关系,可是经过GreetingIntroduceAdvice这座桥梁联系起来 �ӿ�����������ǿ�����Ǵ�� 
	    Apology apology = (Apology) target;
	    apology.saySorry("I'm very excited that everything is OK!");
		
	}
}
