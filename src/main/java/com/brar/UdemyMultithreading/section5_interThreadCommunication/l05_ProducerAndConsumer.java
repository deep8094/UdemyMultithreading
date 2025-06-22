package com.brar.UdemyMultithreading.section5_interThreadCommunication;

import java.util.ArrayList;
import java.util.List;

/**
 * Demonstrates the classic Producer-Consumer problem solution using wait() and notify().
 * 
 * Key Concepts Demonstrated:
 * 1. Thread Synchronization - Using synchronized blocks to protect shared resources
 * 2. Inter-Thread Communication - Using wait() and notify() for thread coordination
 * 3. Producer-Consumer Pattern - A fundamental concurrency design pattern where:
 *    - Producer produces data to a shared buffer
 *    - Consumer consumes data from the same buffer
 *    - Buffer has limited capacity (in this case, max size of 5)
 *
 * How It Works:
 * - When the buffer (list) is full, the producer waits for the consumer
 * - When the buffer is empty, the consumer waits for the producer
 * - notify() is used to wake up waiting threads when conditions change
 * 
 * Key Learnings:
 * - wait() releases the lock and allows other threads to acquire it
 * - notify() wakes up one waiting thread (doesn't release the lock immediately)
 * - The pattern prevents both thread starvation and buffer overflow
 * - Proper synchronization ensures thread safety for the shared list
 */

import static java.lang.Thread.sleep;

class Processor{
    List<Integer> list = new ArrayList<>();
    public void producer() throws InterruptedException {
        synchronized (this) {
            while (true) {
                if (list.size() == 5) {
                    System.out.println("Waiting for consumer to consume");
                    wait();  // Releases lock and waits
                } else {
                    list.add(list.size());
                    System.out.println("Produced: " + list);
                    notify();  // Notify consumer after producing
                    Thread.sleep(1000);  // Optional: slow down production
                }
            }
        }
    }

    public void consumer() throws InterruptedException {
        synchronized (this) {
            while (true) {
                if (list.isEmpty()) {
                    System.out.println("Waiting for producer to produce");
                    wait();  // Releases lock and waits
                } else {
                    int value = list.remove(list.size() - 1);
                    System.out.println("Consumed: " + value + ", Remaining: " + list);
                    notify();  // Notify producer after consuming
                    Thread.sleep(1000);  // Optional: slow down consumption
                }
            }
        }
    }
}

public class l05_ProducerAndConsumer {
    public static void main(String[] args) {
        Processor processor = new Processor();
        Thread t1 = new Thread(new Runnable(){

            @Override
            public void run() {
                try{
                    processor.producer();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread t2 = new Thread(() -> {
            try {
                processor.consumer();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t1.start();
        t2.start();
    }
}
