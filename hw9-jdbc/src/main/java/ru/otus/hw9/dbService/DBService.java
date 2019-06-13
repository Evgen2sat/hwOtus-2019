package ru.otus.hw9.dbService;

public interface DBService<T> {
    void create(T data);
    void update(T data);
    T getItem(long id);
    void createTable();
}
