package com.brar.UdemyMultithreading.section4_thread_Manipulation;

class R1 extends Thread {
    @Override
    public void run() {
        for(int i=1; i <= 10; i++){
            System.out.println("Runner1 : " + i);
            try {
                sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

class R2 extends Thread {

    @Override
    public void run() {
        for(int i=1; i <= 100; i++){
            System.out.println("Runner2 : " + i);
            try {
                sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}


public class l03_thread_Sleep_and_Join {
    public static void main(String[] args) {
        R1 t1 = new R1();
        R2 t2 = new R2();

        t1.start();
        t2.start();

        try{
            t1.join(); //main thread will wait for t1 to complete
            //t2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Finished the tasks...");
    }
}
