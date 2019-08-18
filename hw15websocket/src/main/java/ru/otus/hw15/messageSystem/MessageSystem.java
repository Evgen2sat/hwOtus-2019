package ru.otus.hw15.messageSystem;

import ru.otus.hw15.dto.User;

public interface MessageSystem {
    void sendMessage(Message message);

    void init();

    void addAddresse(Addresse addresse);
}
