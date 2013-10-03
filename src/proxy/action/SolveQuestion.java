package proxy.action;

import proxy.impl.CGLibDynamicProxy;
import proxy.impl.IntroduceQuestionImpl;
import proxy.impl.JDKDynamicProxy;
import proxy.impl.StaticProxy;
import proxy.interf.Question;

public class SolveQuestion {
	public static void main(String[] args) {
		
		//weakness is that code is not flexible and scalable 
		Question question = new IntroduceQuestionImpl();
		question.question("How can I implement dynamic proxy?");
		
		System.out.println("\n----------------------------------\n");
		
		//weakness about static proxy is that will produce many static proxy classes like StaticProxy 
		question = new StaticProxy(new IntroduceQuestionImpl());
		question.question("Is this staic proxy?");

		System.out.println("\n----------------------------------\n");

		//weakness about dynamic proxy is that limited to only these classes implemented interface
		question = new JDKDynamicProxy(new IntroduceQuestionImpl()).getProxy();
		question.question("Is this JDKDynamic proxy?");
		
		System.out.println("\n----------------------------------\n");
		
		//dynamic proxy about method
		question = CGLibDynamicProxy.getInstance().getProxy(IntroduceQuestionImpl.class);
		question.question("Is this suprised CGLibDynamicProxy?");
	
	}
}
