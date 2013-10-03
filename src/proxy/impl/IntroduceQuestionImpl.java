package proxy.impl;

import proxy.interf.Question;

public class IntroduceQuestionImpl implements Question{
	
	public IntroduceQuestionImpl() {
		super();
	}

	public void question(String what) {
		before();
		System.out.println(what);
		after();
	}

	private void before() {
		System.out.println("before000");
	}
	
	private void after() {
		System.out.println("after000");
	}
}
