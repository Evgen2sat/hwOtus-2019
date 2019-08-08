package ru.otus.hw14;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class App {
    private AtomicInteger count = new AtomicInteger(1);
    private AtomicBoolean maxCount = new AtomicBoolean(false);
    private AtomicInteger sequence = new AtomicInteger(2);

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

    private void printCount() {
        while (true) {


            if(Thread.currentThread().getName().equals("Поток 1")) {
                if(sequence.get() !=2) {
                    continue;
                }
                System.out.println(Thread.currentThread().getName() + ": " + count.get());
                sequence.getAndSet(1);
            } else if(Thread.currentThread().getName().equals("Поток 2")) {
                if (sequence.get() !=1) {
                    continue;
                }
                System.out.println(Thread.currentThread().getName() + ": " + count.get());
                if (maxCount.get()) {
                    count.decrementAndGet();

                } else {
                    count.incrementAndGet();
                }
                sequence.getAndSet(2);
            }

            if (count.get() == 10) {
                maxCount.set(true);
            } else if (count.get() == 1) {
                maxCount.set(false);
            }


            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
