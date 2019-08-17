package ru.otus.hw15.messageSystem;

import ru.otus.hw15.dto.User;

public interface MessageSystem {
    void sendMessage(Message message) throws InterruptedException;

    Message<User> createMsgToDatabase(User data);

    Message<User> createMsgToFrontend(User data);

    void init();

    void setDatabaseClient(MessageClient messageClient);

    void setFrontendClient(MessageClient messageClient);
}
