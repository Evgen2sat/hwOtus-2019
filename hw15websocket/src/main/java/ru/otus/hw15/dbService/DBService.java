package ru.otus.hw15.dbService;

import java.util.List;

public interface DBService<T> extends AutoCloseable {
    long create(T data);
    void update(T data);
    T getItem(long id, Class<T> clazz);
    void createTable();
    List<T> getItems();
    T getCreatedItem();
}
