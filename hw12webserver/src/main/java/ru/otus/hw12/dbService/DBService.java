package ru.otus.hw12.dbService;

public interface DBService<T> extends AutoCloseable {
    void create(T data);
    void update(T data);
    T getItem(long id, Class<T> clazz);
    void createTable();
}
