package ru.otus.hw16.messageSystem;

import ru.otus.hw16.dto.User;

import java.io.IOException;
import java.util.List;

public interface FrontendService extends SocketClient {
    void createUser(User user);

    void init() throws IOException;

    void accept(List<User> users);

    void getAllUsers();
}
