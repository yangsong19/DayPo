package spring.aop.impl;

import spring.aop.face.Apology;

public class ApologyImpl implements Apology {

	/* 
	 * 以上这个实现会在运行时自动增强到 GreetingImpl 类中，也就是说，
	 * 无需修改 GreetingImpl 类的代码，让它去实现 Apology 接口，
	 * 我们单独为该接口提供一个实现类（ApologyImpl），来做 GreetingImpl 想做的事情。
	 * 
	 * */
	@Override
	public void saySorry(String word) {
		System.out.println("Sorry, " + word);
	}

}
