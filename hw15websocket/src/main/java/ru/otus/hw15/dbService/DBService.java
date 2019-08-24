package ru.otus.hw15.dbService;

import ru.otus.hw15.messageSystem.Addresse;
import ru.otus.hw15.messageSystem.MessageSystem;

import java.util.List;

public interface DBService<T> extends AutoCloseable, Addresse {
    T create(T data);

    void update(T data);

    T getItem(long id, Class<T> clazz);

    void createTable();

    List<T> getItems();

    void init();
}
