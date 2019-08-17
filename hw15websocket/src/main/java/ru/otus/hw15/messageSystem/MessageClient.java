package ru.otus.hw15.messageSystem;

public interface MessageClient<T> {
    void accept(Message<T> message) throws InterruptedException;
}
