package ru.otus.hw15;

import ru.otus.hw15.dto.User;

import java.util.Optional;

public interface MessageSystem<T> {
    void createItem(T item);

    Optional<T> getCreatedItem();
}
