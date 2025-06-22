package com.brar.UdemyMultithreading.section5_interThreadCommunication;

public class l02_customLocking {
    public static int counter_synchoronized1 = 0;
    public static int counter_synchoronized2 = 0;

    private static final Object lock1 = new Object();
    private static final Object lock2 = new Object();

    public static void incrementCounter1() {
        synchronized(lock1) {
            counter_synchoronized1++;
        }
    }

    public static void incrementCounter2() {
        synchronized(lock2) {  // Different lock
            counter_synchoronized2++;
        }
    }
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new Runnable(){
            @Override
            public void run() {
                for(int i=0; i < 10000; i++){
                    incrementCounter1();
                    incrementCounter2();
                }
            }
        });

        Thread t2 = new Thread(new Runnable(){
            @Override
            public void run() {
                for(int i=0; i < 10000; i++){
                    incrementCounter1();
                    incrementCounter2();
                }
            }
        });
        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("Synchoronized1 : " + counter_synchoronized1 + " Synchoronized2 : " + counter_synchoronized2);
    }
}
