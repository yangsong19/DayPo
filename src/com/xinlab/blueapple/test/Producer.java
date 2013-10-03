package com.xinlab.blueapple.test;

import java.util.concurrent.BlockingDeque;

class Producer implements Runnable {
    private String name;

    private BlockingDeque<Integer> deque;

    public Producer(String name, BlockingDeque<Integer> deque) {
        this.name = name;
        this.deque = deque;
    }

    public synchronized void run() {

        for (int i = 0; i < 10; i++) {
            try {
                deque.putFirst(i);
                System.out.println(name + " puts " + i);
                Thread.sleep(300);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}

class Consumer implements Runnable {
    private String name;

    private BlockingDeque<Integer> deque;

    public Consumer(String name, BlockingDeque<Integer> deque) {
        this.name = name;
        this.deque = deque;
    }

    public synchronized void run() {
        for (int i = 0; i < 10; i++) {
            try {
                int j = deque.takeLast();
                System.out.println(name + " takes " + j);
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}


