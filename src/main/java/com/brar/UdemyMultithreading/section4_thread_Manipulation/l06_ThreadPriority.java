package com.brar.UdemyMultithreading.section4_thread_Manipulation;

public class l06_ThreadPriority {
    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName());
        System.out.println(Thread.currentThread().getPriority()); //default priority is 5

        //setting priority to max does not guarantee that the thread will run immediately
    }
}
