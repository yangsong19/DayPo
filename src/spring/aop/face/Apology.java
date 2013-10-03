package spring.aop.face;

import org.springframework.stereotype.Component;

@Component
public interface Apology {
	
	/*
	 * ���϶����� 但我不想在代码中让 GreetingImpl 直接去实现这个接口，我想在程序运行的时候动态地实现它。
	 * 因为假如我实现了这个接口，那么我就一定要改写 GreetingImpl 这个类，关键是我不想改它，
	 * 或许在真实场景中，这个类有1万行代码，我实在是不敢动了。于是，我需要借助 Spring 的引入增强。
	 * 这个有点意思了！
	 *
	 **/ 
	
	void saySorry(String word);
}
