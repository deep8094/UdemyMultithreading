package com.brar.UdemyMultithreading.section4_thread_Manipulation;

//class R1 implements Runnable {
//    @Override
//    public void run() {
//        for(int i=1; i <= 10; i++){
//            System.out.println("Runner1 : " + i);
//        }
//    }
//}
//
//class R2 implements Runnable {
//
//    @Override
//    public void run() {
//        for(int i=1; i <= 10; i++){
//            System.out.println("Runner2 : " + i);
//        }
//    }
//}

//you can directly extend thread class, but it is not recommended.
class R3 extends Thread {

    @Override
    public void run() {
        for(int i=1; i <= 10; i++){
            System.out.println("Runner3 : " + i);
        }
    }
}

public class l02_threadBasics {
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> { //using lambda
            for(int i=1; i <= 10; i++){
                System.out.println("Runner1 : " + i);
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=1; i <= 10; i++){
                    System.out.println("Runner2 : " + i);
                }
            }
        });

        Thread t3 = new R3();

        t1.start();
        t2.start();
        t3.start();
    }
}
