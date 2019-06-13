package ru.otus.hw9;

import java.lang.annotation.Annotation;

public interface JdbcTemplate<T> {
    void create(T data);
    void update(T data);
    void createOrUpdate(T data);
    T load(long id);
    void createTable();
    boolean checkAnnotation(Object object, Class<? extends Annotation> clazz);
}
