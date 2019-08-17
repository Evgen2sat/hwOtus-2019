package ru.otus.hw15.dbService;

import ru.otus.hw15.dto.User;
import ru.otus.hw15.messageSystem.MessageClient;

import java.util.List;

public interface DBService<T> extends AutoCloseable, MessageClient<T> {
    T create(T data);
    void update(T data);
    T getItem(long id, Class<T> clazz);
    void createTable();
    List<T> getItems();
}
