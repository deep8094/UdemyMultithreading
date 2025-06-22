package com.brar.UdemyMultithreading.section5_interThreadCommunication;

//every thread has its own stack, but all threads share the same heap memory
//stack memory is local to a thread, stores local variables, references and method arguments
// head memory is global, stores objects.

/*
* Problem with synchronization :
* Intrinsic lock is acquired at class level, resulting in single lock across all synchronized methods/blocks
* */
public class l01_synchonization {
    public static int counter_synchoronized1 = 0;
    public static int counter_synchoronized2 = 0;
    public static int counter_unsynchoronized = 0;

    //because class object has single lock : this is why below 2 methods cannot be executed at same time - off-course will use time slicing algo.
    public static synchronized void incrementCounter1() {
        counter_synchoronized1++;
    }

    public static synchronized void incrementCounter2() {
        counter_synchoronized2++;
    }

    public static  void incrementCounter3() {
        counter_unsynchoronized++;
    }
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new Runnable(){
            @Override
            public void run() {
                for(int i=0; i < 10000; i++){
                    incrementCounter3();
                    incrementCounter1();
                    incrementCounter2();
                }
            }
        });

        Thread t2 = new Thread(new Runnable(){
            @Override
            public void run() {
                for(int i=0; i < 10000; i++){
                    incrementCounter3();
                    incrementCounter1();
                    incrementCounter2();
                }
            }
        });
        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("Unsynchoronized : " + counter_unsynchoronized + " Synchoronized1 : " + counter_synchoronized1 + " Synchoronized2 : " + counter_synchoronized2);
    }
}
