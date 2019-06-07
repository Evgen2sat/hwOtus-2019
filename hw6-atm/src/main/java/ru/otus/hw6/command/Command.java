package ru.otus.hw6.command;

public interface Command<T> {
    T execute();
}
