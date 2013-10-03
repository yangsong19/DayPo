package com.xinlab.blueapple.multithread;
/*
 * This program does not use Concurrent collections, nor Lock or Condition. It only uses Synchronized.
 * What does this program do? The logic is:
 * 1) There are 1 SProducer and 3 Carriers. Producer produces numbers into a queue. 
 *    Carriers fetch numbers from queue.
 * 2) Producers will have a relax when size of the queue exceeds 10.
 * 3) Carrier will wait when queue is empty.
 * 4) If Carrier fetches a number ends with 4, Carrier will start a strike.
 * 5) The strike will initiate a Negotiation.
 * 6) During Negotiation, Producer and Carriers won't work.
 * 7) The Negotiation result depends on the Number fetched which leads to strike. If the number begins
 *    with 3 or 7, Negotiation fails. Otherwise, Negotiation succeeds.
 * 8) Producer and Carriers go back to work if result of Negotiation is successful. 
 *    Otherwise, Process stops.
 */
import java.util.ArrayList;
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
                sleep(5000);
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
                            sleep(1000);
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
        int i = Integer.MAX_VALUE;//2147483647
    }
}