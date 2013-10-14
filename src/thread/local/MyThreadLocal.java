package thread.local;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MyThreadLocal<T> {
	private Map<Thread, T> map = Collections.synchronizedMap(new HashMap<Thread, T>());//这样一个初始化，导致的结果是Thread,T的值均是null
	
	public void set(T value) {
		map.put(Thread.currentThread(), value);
	}
	
	public T get() {
		Thread thread = Thread.currentThread();
		T value = map.get(thread);
		if(value == null && ! map.containsKey(thread)) {//第一次看到这样一个判断条件时，觉得其似乎很奇怪，然而你想想整个内部环境及外部调用环境了吗
			value = initialValue();
			map.put(thread, value);
		}
		return value;
	}

	public void remove() {
		map.remove(Thread.currentThread());
	}
	
	protected T initialValue() {
		return null;
	}
}
