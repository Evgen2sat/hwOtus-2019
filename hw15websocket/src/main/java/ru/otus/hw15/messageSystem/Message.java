package ru.otus.hw15.messageSystem;

import java.util.concurrent.ArrayBlockingQueue;

public abstract class Message<T> {
    private T data;

    private ArrayBlockingQueue<Message> queue;

    public void setQueueTo(ArrayBlockingQueue<Message> queue) {
        this.queue = queue;
    }

    public ArrayBlockingQueue<Message> getQueueTo() {
        return queue;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
