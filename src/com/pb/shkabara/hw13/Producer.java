package com.pb.shkabara.hw13;

import java.util.Queue;

// Производитель
public class Producer implements Runnable {
    // Общая очередь
    private final Queue<Double> sharedQueue;
    // Максимальный размер очереди
    private final int maxSize;

    public Producer(Queue<Double> sharedQueue, int maxSize) {
        this.sharedQueue = sharedQueue;
        this.maxSize = maxSize;
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            try {
                // В цикле вызывается метод produce
                System.out.println("Produced: " + produce());
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    private double produce() throws InterruptedException {
        synchronized (sharedQueue) { // обязательно synchronized
            if (sharedQueue.size() == maxSize) {
                // Если очередь полна, то ждём
                System.out.println("Producer waiting...");
                // освобождает монитор и переводит вызывающий поток в состояние ожидания до тех пор,
                // пока другой поток не вызовет метод notify() или notifyAll()
                sharedQueue.wait();
            }

            // Добавили элемент в очередь.
            double newValue = Math.random();
            sharedQueue.add(newValue);

            // Уведомили другой поток на случай, если он ждет
            sharedQueue.notifyAll();

            return newValue;
        }
    }
}
