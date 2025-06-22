package com.brar.UdemyMultithreading.section5_interThreadCommunication;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Demonstrates the Producer-Consumer pattern using ReentrantLock and Condition variables.
 * 
 * Key Differences from synchronized version:
 * 1. Uses ReentrantLock instead of synchronized blocks
 * 2. Uses Condition objects for thread signaling (replaces wait/notify)
 * 3. Provides more flexible locking (tryLock, lockInterruptibly, etc.)
 * 4. Supports fairness policy
 * 
 * Advantages:
 * - More flexible than synchronized blocks
 * - Can have multiple condition variables per lock
 * - Can attempt to acquire lock without blocking
 * - Can specify timeout when waiting for a condition
 */

class ProcessorWithLock {
    private final Queue<Integer> queue = new LinkedList<>();
    private static final int CAPACITY = 5;
    private final Lock lock = new ReentrantLock();
    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();

    public void produce() throws InterruptedException {
        int value = 0;
        while (true) {
            lock.lock();
            try {
                // Wait if queue is full
                while (queue.size() == CAPACITY) {
                    System.out.println("Queue is full, producer waiting...");
                    notFull.await();
                }
                
                // Produce value
                System.out.println("Produced: " + value);
                queue.add(value++);
                
                // Signal consumer that queue is not empty
                notEmpty.signal();
                
                // Slow down producer for demo purposes
                Thread.sleep(300);
            } finally {
                lock.unlock();
            }
        }
    }

    public void consume() throws InterruptedException {
        while (true) {
            lock.lock();
            try {
                // Wait if queue is empty
                while (queue.isEmpty()) {
                    System.out.println("Queue is empty, consumer waiting...");
                    notEmpty.await();
                }
                
                // Consume value
                int value = queue.poll();
                System.out.println("Consumed: " + value + ", Queue size: " + queue.size());
                
                // Signal producer that queue is not full
                notFull.signal();
                
                // Slow down consumer for demo purposes
                Thread.sleep(500);
            } finally {
                lock.unlock();
            }
        }
    }
}

public class l07_ProducerAndConsumer_withLock {
    public static void main(String[] args) {
        ProcessorWithLock processor = new ProcessorWithLock();
        
        // Create producer thread
        Thread producerThread = new Thread(() -> {
            try {
                processor.produce();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Producer was interrupted");
            }
        }, "Producer");
        
        // Create consumer thread
        Thread consumerThread = new Thread(() -> {
            try {
                processor.consume();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Consumer was interrupted");
            }
        }, "Consumer");
        
        // Start both threads
        producerThread.start();
        consumerThread.start();
        
        // Let it run for 5 seconds
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Interrupt both threads
//        producerThread.interrupt();
//        consumerThread.interrupt();
        
        System.out.println("Demo completed!");
    }
}
