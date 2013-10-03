package proxy.impl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

public class JDKDynamicProxy implements InvocationHandler{
	
	private Object target;
	
	public JDKDynamicProxy(Object target) {
		this.target = target;
	}

	/**
	 * @param <T>
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T getProxy() {
		return (T) Proxy.newProxyInstance(target.getClass().getClassLoader(), 
				target.getClass().getInterfaces(), 
				this);//this->implements the interface InvocationHandler
	}
	
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		System.out.println("proxy:"+proxy.getClass().getName()+";method:"+method.getName()+";args:"+Arrays.toString(args));
		before();
		Object result = method.invoke(target, args);
		after();
		return result;
	}

	private void after() {
		System.out.println("after222");
	}

	private void before() {
		System.out.println("before222");
	}

}
