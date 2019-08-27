package ru.otus.hw16.messageSystem;

import ru.otus.hw16.messageSystem.message.Message;

import java.net.Socket;

public interface MessageSystem {
    void sendMessage(Message message);

    void addSocket(Socket socket);
}
