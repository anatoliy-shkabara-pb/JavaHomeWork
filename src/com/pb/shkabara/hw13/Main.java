package com.pb.shkabara.hw13;

import java.util.LinkedList;

public class Main {
    public static void main(String[] args) throws Exception {
        LinkedList<Double> sharedQueue = new LinkedList<>();
        int size = 4;
        Thread prodThread = new Thread(new Producer(sharedQueue, size), "Producer");
        Thread consThread = new Thread(new Consumer(sharedQueue), "Consumer");
        prodThread.start();
        consThread.start();

        Thread.sleep(100);

        prodThread.interrupt();
        consThread.interrupt();
    }
}
