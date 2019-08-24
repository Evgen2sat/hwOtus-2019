package ru.otus.hw16.messageSystem;

import ru.otus.hw16.dto.User;

import java.util.List;

public interface FrontendService extends Addresse {
    void createUser(User user);

    void init();

    void accept(List<User> users);

    void getAllUsers();
}
