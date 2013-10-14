package thread.local;

public class SequenceC implements Sequence {

	//为什么 initialValue() 方法是 protected 的呢？就是为了提醒程序员们，这个方法是要你们来实现的，请给这个线程局部变量一个初始值吧。 
	private static MyThreadLocal<Integer> numberContainer = new MyThreadLocal<Integer>() {
		@Override
		protected Integer initialValue() {
			return 0;
		}
	};
	
	public int getNumber() {
		numberContainer.set(numberContainer.get() + 1);
		return numberContainer.get();
	}
	
	public static void main(String[] args) {
		Sequence sequence = new SequenceC();
		
		ClientThread thread1 = new ClientThread(sequence);
		ClientThread thread2 = new ClientThread(sequence);
		ClientThread thread3 = new ClientThread(sequence);
		
		thread1.start();
		thread2.start();
		thread3.start();
		
	}

}
