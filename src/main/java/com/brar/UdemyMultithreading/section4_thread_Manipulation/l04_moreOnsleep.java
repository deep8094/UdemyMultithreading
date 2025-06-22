package com.brar.UdemyMultithreading.section4_thread_Manipulation;

import static java.lang.Thread.sleep;

class Worker implements Runnable {
    private boolean isTerminated = false;

    @Override
    public void run() {
        while(!isTerminated){
            System.out.println("Hello from worker class...");
        }
        try {
            sleep(300);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isTerminated() {
        return isTerminated;
    }

    public void setTerminated(boolean terminated) {
        isTerminated = terminated;
    }
}


public class l04_moreOnsleep{
    public static void main(String[] args){
        Worker worker = new Worker();
        Thread t1 = new Thread(worker);
        t1.start();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        worker.setTerminated(true);
        System.out.println("Finished the tasks...");
    }
}
