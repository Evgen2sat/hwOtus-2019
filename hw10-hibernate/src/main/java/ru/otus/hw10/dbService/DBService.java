package ru.otus.hw10.dbService;

public interface DBService<T> {
    void create(T data);
    void update(T data);
    T getItem(long id, Class<T> clazz);
    void createTable();
}
