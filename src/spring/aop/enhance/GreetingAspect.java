package spring.aop.enhance;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.DeclareParents;
import org.springframework.stereotype.Component;

import spring.aop.face.Apology;
import spring.aop.impl.ApologyImpl;

@Aspect
@Component("aspect")
public class GreetingAspect {
	
/*	
 *  引入增强	
    value：目标类
    defaultImpl：引入接口的默认实现类
 *
 */
	@DeclareParents(value="spring.aop.impl.GreetingImpl", defaultImpl=ApologyImpl.class)
	private Apology apology;
	
	 /*
	  * 注意：类上面标注的 @Aspect 注解，这表明该类是一个 Aspect（其实就是 Advisor）。
	    该类无需实现任何的接口，只需定义一个方法（方法叫什么名字都无所谓），只需在方法上
	    标注 @Around 注解，在注解中使用了 AspectJ 切点表达式。方法的参数中包括一个 
	    ProceedingJoinPoint 对象，它在 AOP 中称为 Joinpoint（连接点），可以通过该对象获取方法
	    的任何信息，例如：方法名、参数等。  
	    下面重点来分析一下这个切点表达式：  
	    execution(* aop.demo.GreetingImpl.*(..))  execution()：表示拦截方法，括号中可定义需要
	    匹配的规则。 第一个“*”：表示方法的返回值是任意的。 第二个“*”：表示匹配该类中所有的方法。 
	    (..)：表示方法的参数是任意的。  是不是比正则表达式的可读性更强呢？如果想匹配指定的方法，只
	    需将第二个“*”改为指定的方法名称即可。
	   *
	   *
	   */
//	@Around("execution(* spring.aop.impl.GreetingImpl.*(**))")
//	用自定义的注解,这次使用了 @annotation() 表达式，只需在括号内定义需要拦截的注解名称即可
/*	
 *  除了 @Around 注解外，其实还有几个相关的注解，稍微归纳一下吧：
	    @Before：前置增强
	    @After：后置增强
	    @Around：环绕增强
	    @AfterThrowing：抛出增强
	    @DeclareParents：引入增强
	此外还有一个 @AfterReturning（返回后增强），也可理解为 Finally 增强，相当于 finally 语句，它是在方法结束后执行的，也就说说，它比 @After 还要晚一些。
	最后一个 @DeclareParents 竟然就是引入增强！为什么不叫做 @Introduction 呢？我也不知道为什么，但它干的活就是引入增强。 
 *
 */
	@Around("@annotation(spring.aop.face.Tag)")
	public Object around(ProceedingJoinPoint pjp) throws Throwable {
		before();
		Object result = pjp.proceed();
		after();
		return result;
	}

	private void after() {
		System.out.println("After");
	}

	private void before() {
		System.out.println("Before");
	}
}
