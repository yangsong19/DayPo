package thread.local;

public class SequenceB implements Sequence {

	//为什么 initialValue() 方法是 protected 的呢？就是为了提醒程序员们，这个方法是要你们来实现的，请给这个线程局部变量一个初始值吧。 
	private static ThreadLocal<Integer> numberContainer = new ThreadLocal<Integer>() {
		@Override
		protected Integer initialValue() {
			return 0;
		}
	};
	
	public int getNumber() {
		numberContainer.set(numberContainer.get() + 1);
		return numberContainer.get();
	}
	
/*	 
 * 通过 ThreadLocal 封装了一个 Integer 类型的 numberContainer 静态成员变量，并且初始值是0。再看 getNumber() 方法，
 * 首先从 numberContainer 中 get 出当前的值，加1，随后 set 到 numberContainer 中，最后将 numberContainer 中 get 
 * 出当前的值并返回。	 是不是很恶心？但是很强大！确实稍微饶了一下，我们不妨把 ThreadLocal 看成是一个容器，这样理解
 * 就简单了。所以，这里故意用 Container 这个单词作为后缀来命名 ThreadLocal 变量。 
 * 
 * */
	
	
	public static void main(String[] args) {
		Sequence sequence = new SequenceB();
		
		ClientThread thread1 = new ClientThread(sequence);
		ClientThread thread2 = new ClientThread(sequence);
		ClientThread thread3 = new ClientThread(sequence);
		
		thread1.start();
		thread2.start();
		thread3.start();
		
	}
/*	
	Thread-0 => 1
	Thread-1 => 2
	Thread-0 => 3
	Thread-1 => 4
	Thread-0 => 5
	Thread-1 => 6
	Thread-2 => 7
	Thread-2 => 8
	Thread-2 => 9

	由于线程启动顺序是随机的，所以并不是0、1、2这样的顺序,这个好理解.为什么当 Thread-0 输出了1、2、3之后,
	而 Thread-2 却输出了4、5、6呢?线程之间竟然共享了 static 变量!这就是所谓的【非线程安全】问题了。
	
	-------->
	
	Thread-0 => 1
	Thread-2 => 1
	Thread-2 => 2
	Thread-2 => 3
	Thread-1 => 1
	Thread-1 => 2
	Thread-1 => 3
	Thread-0 => 2
	Thread-0 => 3
	
	每个线程相互独立了，同样是 static 变量，对于不同的线程而言，它没有被共享，而是每个线程各一份，这样也就保
	证了线程安全。 也就是说，TheadLocal 为每一个线程提供了一个独立的副本！
*
*/
}
