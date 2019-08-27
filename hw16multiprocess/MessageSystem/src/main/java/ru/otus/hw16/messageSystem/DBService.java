package ru.otus.hw16.messageSystem;

import java.util.List;

public interface DBService<T> extends AutoCloseable, Addresse, SocketClient {
    T create(T data);

    void update(T data);

    T getItem(long id, Class<T> clazz);

    void createTable();

    List<T> getItems();

    void init();
}
