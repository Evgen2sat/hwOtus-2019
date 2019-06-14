package ru.otus.hw9.v2.jdbcTemplate;

import ru.otus.hw9.SQLForCreateTableEnum;

import java.lang.annotation.Annotation;

public interface JdbcTemplate<T> {
    void create(T data);
    void update(T data);
    void createOrUpdate(T data);
    T load(long id, Class<T> clazz);
    void createTable(SQLForCreateTableEnum type);
    boolean checkAnnotation(Object object);
}
