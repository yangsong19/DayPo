package com.xinlab.blueapple.test;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class BlockingDequeTester {
    public static void main(String[] args) {
        BlockingDeque<Integer> deque = new LinkedBlockingDeque<Integer>(5);
        Runnable producer = new Producer("Producer", deque);
        Runnable consumer = new Consumer("Consumer", deque);
        new Thread(producer).start();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(consumer).start();
    }
}
