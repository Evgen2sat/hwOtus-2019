package ru.otus.hw15.frontendService;

import ru.otus.hw15.dto.User;
import ru.otus.hw15.messageSystem.Addresse;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public interface FrontendService extends Addresse {
    void createUser(User user);

    void init();

    void accept(List<User> users);

    void getAllUsers();
}
