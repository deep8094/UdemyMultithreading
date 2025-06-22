package com.brar.UdemyMultithreading.section5_interThreadCommunication;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Demonstrates the use of ReentrantLock in Java for thread synchronization.
 * 
 * Key Concepts:
 * 1. ReentrantLock - A more flexible alternative to synchronized blocks/methods
 * 2. Lock Acquisition - Using lock() and tryLock() methods
 * 3. Proper Lock Release - Using try-finally to ensure locks are always released
 * 4. Fairness Policy - Demonstrates fair vs unfair lock acquisition
 * 
 * Advantages over synchronized:
 * - Ability to try acquiring the lock (tryLock())
 * - Interruptible lock acquisition (lockInterruptibly())
 * - Fair lock acquisition (first-come-first-served)
 * - Ability to check if the lock is held (isHeldByCurrentThread())
 */

class Counter {
    private int count = 0;
    // Create a ReentrantLock with fairness policy (true = fair, false = unfair)
    private final Lock lock = new ReentrantLock(true);

    public void increment() {
        // Lock the critical section
        lock.lock();
        try {
            count++;
            System.out.println(Thread.currentThread().getName() + " increments to: " + count);
        } finally {
            // Always release the lock in finally block
            lock.unlock();
        }
    }

    public void tryIncrement() {
        // Try to acquire the lock without waiting
        boolean acquired = lock.tryLock();
        if (acquired) {
            try {
                System.out.println(Thread.currentThread().getName() + " acquired lock");
                count++;
                Thread.sleep(1000); // Simulate work
                System.out.println(Thread.currentThread().getName() + " increments to: " + count);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                lock.unlock();
            }
        } else {
            System.out.println(Thread.currentThread().getName() + " could not acquire lock, doing something else...");
        }
    }

    public int getCount() {
        return count;
    }
}

public class l06_locks {
    public static void main(String[] args) throws InterruptedException {
        Counter counter = new Counter();
        
        System.out.println("=== Basic Locking Example ===");
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                counter.increment();
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }, "Thread-1");

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                counter.increment();
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }, "Thread-2");

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println("\n=== Try Lock Example ===");
        Thread t3 = new Thread(() -> counter.tryIncrement(), "Thread-3");
        Thread t4 = new Thread(() -> counter.tryIncrement(), "Thread-4");
        
        t3.start();
        t4.start();
        t3.join();
        t4.join();
        
        System.out.println("Final count: " + counter.getCount());
    }
}
