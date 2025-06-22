package com.brar.UdemyMultithreading.section4_thread_Manipulation;

class Runner1{
    void startRunning(){
        for(int i=1; i <= 10; i++){
            System.out.println("Runner1 : " + i);
        }
    }
}

class Runner2{
    void startRunning(){
        for(int i=1; i <= 10; i++){
            System.out.println("Runner2 : " + i);
        }
    }
}

public class l01_sequential {
    public static void main(String[] args) {
        Runner1 runner1 = new Runner1();
        Runner2 runner2 = new Runner2();

        runner1.startRunning();
        runner2.startRunning();
    }
}
