package spring.aop.enhance;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.support.DelegatingIntroductionInterceptor;
import org.springframework.stereotype.Component;

import spring.aop.face.Apology;

@Component("introduce")
public class GreetingIntroduceAdvice extends DelegatingIntroductionInterceptor implements Apology{

	/*
	 *  以上定义了一个引入增强类，扩展了 org.springframework.aop.support.DelegatingIntroductionInterceptor 类，
	 *  同时也实现了新定义的 Apology 接口。在类中首先覆盖了父类的 invoke() 方法，然后实现了 Apology 接口的方法。
	 *  我就是想用这个增强类去丰富 GreetingImpl 类的功能，那么这个 QuestionImpl 类无需直接实现 Apology 接口，
	 *  就可以在程序运行的时候调用 Apology 接口的方法了。这简直是太神奇的！
	 *  
	 * */
	
	private static final long serialVersionUID = -6869075934191924711L;

	@Override
	protected Object doProceed(MethodInvocation invocation) throws Throwable {
		return super.doProceed(invocation);
	}

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		return super.invoke(invocation);
	}

	public void saySorry(String word) {
		System.out.println(word);
	}

}
