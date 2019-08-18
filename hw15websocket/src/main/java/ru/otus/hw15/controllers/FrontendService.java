package ru.otus.hw15.controllers;

import ru.otus.hw15.dto.User;
import ru.otus.hw15.messageSystem.Addresse;

public interface FrontendService extends Addresse {
    void createUser(User user);

    void init();

    void accept(User user);
}
