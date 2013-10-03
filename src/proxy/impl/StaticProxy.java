package proxy.impl;

import proxy.interf.Question;

public class StaticProxy implements Question{

	private IntroduceQuestionImpl introduceQuestion;
	
	public StaticProxy(IntroduceQuestionImpl introduceQuestion) {
		this.introduceQuestion = introduceQuestion;
	}

	public void question(String what) {
		before();
		this.introduceQuestion.question(what);
		after();
	}

	private void after() {
		System.out.println("after111");
	}

	private void before() {
		System.out.println("before111");
	}

}
