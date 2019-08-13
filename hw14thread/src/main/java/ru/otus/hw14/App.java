package ru.otus.hw14;

import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class App {
    private AtomicInteger count = new AtomicInteger(1);
    private AtomicBoolean maxCount = new AtomicBoolean(false);
    private AtomicLong lastActiveThreadId = new AtomicLong();
    private AtomicInteger counterCallMethod = new AtomicInteger(1);

    public static void main(String[] args) {
        App app = new App();
        app.startThreads();
    }

    private void startThreads() {
        Thread thread1 = new Thread(this::printCount);
        thread1.setName("Поток 1");

        Thread thread2 = new Thread(this::printCount);
        thread2.setName("Поток 2");

        lastActiveThreadId.set(thread2.getId());

        thread1.start();
        thread2.start();
    }

    private synchronized void printCount() {
        while (true) {

            if (lastActiveThreadId.get() == Thread.currentThread().getId()) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            System.out.println(Thread.currentThread().getName() + ": " + count.get());
            lastActiveThreadId.getAndSet(Thread.currentThread().getId());

            if (counterCallMethod.get() % 2 == 0) {
                if (maxCount.get()) {
                    count.decrementAndGet();
                } else {
                    count.incrementAndGet();
                }
            }

            if (count.get() == 10) {
                maxCount.set(true);
            } else if (count.get() == 1) {
                maxCount.set(false);
            }

            counterCallMethod.incrementAndGet();

            notify();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}