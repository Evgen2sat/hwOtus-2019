package ru.otus.hw14;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class App {
    private AtomicInteger count = new AtomicInteger(1);
    private AtomicBoolean maxCount = new AtomicBoolean(false);
    private AtomicInteger sequence = new AtomicInteger(0);

    public static void main(String[] args) {
        App app = new App();
        app.startThreads();
    }

    private void startThreads() {
        Thread thread1 = new Thread(this::printCount);
        thread1.setName("Поток 1");

        Thread thread2 = new Thread(this::printCount);
        thread2.setName("Поток 2");

        thread1.start();
        thread2.start();
    }

    private  void printCount() {
        while (true) {

            System.out.println(Thread.currentThread().getName() + ": " + count);

            sequence.incrementAndGet();

            if (count.get() == 10) {
                maxCount.set(true);
            } else if (count.get() == 1) {
                maxCount.set(false);
            }

            if (maxCount.get()) {
                if(sequence.get() % 2 == 0) {
                    count.decrementAndGet();
                }

            } else {
                if(sequence.get() % 2 == 0) {
                    count.incrementAndGet();
                }
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
