package ru.otus.hw15.frontendService;

import ru.otus.hw15.dto.User;
import ru.otus.hw15.messageSystem.Addresse;

import java.util.concurrent.ArrayBlockingQueue;

public interface FrontendService extends Addresse {
    void createUser(User user);

    void init();

    void accept(User user);

    ArrayBlockingQueue<User> getCreatedUsers();
}
