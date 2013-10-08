package thread.local;

public class SequenceB implements Sequence {

	private static int number = 0;
	public int getNumber() {
		number += 1;
		return number;
	}
	
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
*/
}
