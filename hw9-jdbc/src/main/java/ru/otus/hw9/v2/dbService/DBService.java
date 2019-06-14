package ru.otus.hw9.v2.dbService;

import ru.otus.hw9.SQLForCreateTableEnum;

public interface DBService<T> {
    void create(T data);
    void update(T data);
    T getItem(long id, Class<T> clazz);
    void createTable();
}
