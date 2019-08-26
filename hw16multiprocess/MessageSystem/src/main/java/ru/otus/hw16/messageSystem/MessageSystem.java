package ru.otus.hw16.messageSystem;

import ru.otus.hw16.messageSystem.message.Message;

public interface MessageSystem {
    void sendMessage(Message message);

    void addAddresse(Addresse addresse);
}
