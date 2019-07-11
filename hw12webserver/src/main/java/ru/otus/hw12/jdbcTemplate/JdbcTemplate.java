package ru.otus.hw12.jdbcTemplate;

import ru.otus.hw12.SQLForCreateTableEnum;

public interface JdbcTemplate<T> {
    void create(T data);
    void update(T data);
    void createOrUpdate(T data);
    T load(long id, Class<T> clazz);
    void createTable(SQLForCreateTableEnum type);
    boolean checkAnnotation(Object object);
}
