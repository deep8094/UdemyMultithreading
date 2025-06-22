package com.brar.UdemyMultithreading.section4_thread_Manipulation;

import static java.lang.Thread.sleep;

class DaemonWorker implements Runnable {

    @Override
    public void run() {
        while(true){
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Daemon thread is running...");
        }
    }
}

class NormaWorker implements Runnable {

    @Override
    public void run() {
        System.out.println("Normal thread is running...");
        try {
            sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

public class l05_Daemon_and_User_Threads {
    public static void main(String[] args) {
        Thread t1 = new Thread(new DaemonWorker());
        Thread t2 = new Thread(new NormaWorker());
        t1.setDaemon(true); //low priority threads, run in background, eg garbage collection. will be terminated  by JVM when main thread terminates
        t1.start(); //will end as soon as main thread ends
        t2.start(); //worker by default

        System.out.println(t1.isDaemon());
    }
}
