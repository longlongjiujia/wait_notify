package com.jd.lombok;

import org.junit.Test;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class SimplePandC {


    final Queue<Integer> queue = new LinkedList<>();

    int maxSize = 100;


    @Test
    public void test() {
        Producer producer = new Producer();
        Consumer consumer = new Consumer();
        Thread producerThread = new Thread(producer);
        Thread consumerThread = new Thread(consumer);
        consumerThread.start();
        producerThread.start();

        while (true) {

        }
    }

    class Producer implements Runnable {

        @Override
        public void run() {
            while (true) {
                synchronized (queue) {
                    while (queue.size() == maxSize) {
                        try {
                            System.out.println("Queue is full, " + "Producer thread waiting for " + "consumer to take something from queue");
                            queue.wait();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                    Random random = new Random();
                    int i = random.nextInt();
                    System.out.println("Producing value : " + i);
                    queue.add(i);
                    queue.notifyAll();
                }
            }
        }
    }


    class Consumer implements Runnable {

        @Override
        public void run() {
            while (true) {
                synchronized (queue) {
                    while (queue.isEmpty()) {
                        System.out.println("Queue is empty," + "Consumer thread is waiting" + " for producer thread to put something in queue");
                        try {
                            queue.wait();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                    System.out.println("Consuming value : " + queue.remove());
                    queue.notifyAll();
                }
            }
        }
    }


}
