package com.xinlab.blueapple.contenttool.thread;


import java.util.ArrayList;
/**
 * @author Administrator
 *	这段代码虽为多线程,但没有使用并发集合,也没有使用Lock和Condition.全部使用Synchronized来完成的.
	代码的逻辑:
	1)SProducer不停的产生number到queue中.
	2)3个carrier不停的取出queue中的number.
	3)如果queue中存在10个剩余number时,SProducer会停下来等Carrier消费一些后再生产.
	4)如果Carrier发现queue中没有number时,会等待.
	5)如果Carrier取出的数字末尾为4, 则会挑起罢工事件.
	6)Carrier罢工会引发一个Negotiation线程进行谈判.
	7)罢工阶段SProducer和所有Carrier均停工.
	8)Negotiation如果发现取出的number首位为3或者7,将引发谈判失败.
	9)如果谈判成功,则恢复工作,如果谈判失败,则破产,所有线程均退出.
 */
public class SProducer extends Thread {
    // Note: ArrayList is not thread-safe collection.
    private final static ArrayList<String> numbers = new ArrayList<String>();
    private final static ArrayList<Thread> threads = new ArrayList<Thread>();
    private volatile boolean negotiating  = false;
    private class Negotiation implements Runnable {
        private String number;
        private Negotiation(String number) {
            this.number = number;
        }
        public void run() {
            try {
                System.out.println("Start negotiation...");
                sleep(00);
                if (number.startsWith("7") || number.startsWith("3")) {
                    System.out.println("Negotiation broken.");
                    for (Thread t : threads) {
                        t.interrupt();
                    }
                    return;
                }
                System.out.println("Negotiation succeeds.");
            } catch (InterruptedException e) {
                System.out.println("Middle man is killed.");
            }
            synchronized(Negotiation.class) {
                negotiating = false;
                Negotiation.class.notifyAll();
            }
        }
    }
     
    private class Carrier implements Runnable {
        private String name;
        private Carrier(String name) {
            this.name = name;
        }
        private boolean negotiating(boolean atBeginning) {
            synchronized (Negotiation.class) {
                while (negotiating) {
                    try {
                        System.out.println("Carrier ["+name+"] join stricks.");
                        Negotiation.class.wait();
                    } catch (InterruptedException e) {
                        System.out.println("Negotiation fails. Carrier [" + name + "] retires.");
                        return false;
                    }
                }
            }
            return true;
        }
        public void run() {
            while(true) {
                if(negotiating && !this.negotiating(true))return;
                 
                synchronized(numbers) {
                    while (numbers.size() == 0) {
                        try {
                            System.out.println("List empty. Carrier [" + name + "] waiting.");
                            numbers.wait();
                        } catch (InterruptedException e) {
                            System.out.println("Negotiation fails. Carrier [" + name + "] retires.");
                            return;
                        }
                    }
                }
                 
                String number;
                synchronized(numbers) {
                    number = numbers.remove(0);
                    System.out.println("Carrier [" + name + "] carries "+ number +" out of List;");
                    numbers.notifyAll();
                }
                 
                if (number.endsWith("4")) {
                    synchronized (Negotiation.class) {
                        if(negotiating && !this.negotiating(false))return;
                        try {
                            sleep(0);
                        } catch (Exception e){
                        }
                        negotiating = true;
                        System.out.println("Stricks happen on number:"+number);
                        new Thread(new Negotiation(number)).start();
                         
                        if(!this.negotiating(true))return;
                    }
                }
            }
        }
    }
     
    public void run() {
        Thread a = new Thread(new Carrier("a"));
        Thread b = new Thread(new Carrier("b"));
        Thread c = new Thread(new Carrier("c"));
        threads.add(this);
        threads.add(a);
        threads.add(b);
        threads.add(c);
         
        a.start();
        b.start();
        c.start();
         
        this.produceNumbers();
         
    }
     
    private void produceNumbers() {
        while (true) {
            synchronized (Negotiation.class) {
               while (negotiating) {
                    try {
                        System.out.println("Stricking... Producer waiting for negotiation result.");
                        Negotiation.class.wait();
                        System.out.println("Negotiation succeeds. Producer happy.");
                    } catch (InterruptedException e) {
                        System.out.println("Negotiation fails. Producer breaks up.");
                        return;
                    }
                }
            }
             
            String number = ""+new java.util.Random().nextInt(47);
            // Need a lock for non-thread-safe list.
             
            synchronized(numbers) {
                numbers.add(number);
                System.out.println("Produce number " + number + " into List;");
                numbers.notifyAll();
            }
             
            synchronized(numbers) {
                while (numbers.size() > 10) {
                    try {
                        numbers.wait();
                    } catch (InterruptedException e) {
                        System.out.println("Negotiation fails. Producer breaks up.");
                        return;
                    }
                }
            }
        }
    }
     
    public static void main(String[] args) {
        new SProducer().start();
    }
}
