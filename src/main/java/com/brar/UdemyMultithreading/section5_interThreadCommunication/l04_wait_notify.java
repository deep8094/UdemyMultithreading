package com.brar.UdemyMultithreading.section5_interThreadCommunication;

import java.util.ArrayList;
import java.util.List;

/**
 * Demonstrates the use of wait() and notify() for inter-thread communication in Java.
 * 
 * Key Concepts:
 * 1. wait() - Causes the current thread to wait until another thread invokes notify() or notifyAll()
 *    - Must be called from synchronized context (inside synchronized block/method)
 *    - Releases the lock on the object it's called on
 *    - Thread goes to WAITING state until notified or interrupted
 * 
 * 2. notify() - Wakes up a single thread waiting on this object's monitor
 *    - Must be called from synchronized context
 *    - Does NOT release the lock immediately - the lock is only released when the synchronized block ends
 *    - If multiple threads are waiting, one is chosen arbitrarily
 * 
 * 3. Common Usage Pattern:
 *    synchronized(lock) {
 *        while(conditionNotMet) {
 *            lock.wait();
 *        }
 *        // Perform action
 *    }
 * 
 * 4. Important Notes:
 *    - Always call wait() in a loop that checks the condition
 *    - Use notifyAll() instead of notify() if multiple threads might be waiting
 *    - wait() and notify() must be called on the same object that's used for synchronization
 * 
 * In this example:
 * - The producer thread waits until consumer notifies it
 * - The consumer thread notifies the producer after some work
 * - Shows that notify() doesn't immediately release the lock
 */

class Process{
    public void producer() throws InterruptedException {
        synchronized (this) {
            System.out.println("Producer is running");
            wait();
            System.out.println("Again producer is running");
        }
    }

    public void consumer() throws InterruptedException {
        synchronized (this) {
            Thread.sleep(500);
            System.out.println("Consumer is running");
            notify();
            System.out.println("Notify will not handle the lock immediately, it will complete further operations");
        }
    }
}
public class l04_wait_notify {
    public static void main(String[] args) {
        Process process = new Process();
        Thread t1 = new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    process.producer();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        Thread t2 = new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    process.consumer();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        t1.start();
        t2.start();
    }
}