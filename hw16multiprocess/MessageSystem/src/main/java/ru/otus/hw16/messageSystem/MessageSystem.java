package ru.otus.hw16.messageSystem;

import ru.otus.hw16.messageSystem.message.Message;

import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;

public interface MessageSystem {
    void sendMessage(Socket socket, Message message);

    void addSocket(Socket socket);

    void registerSocketClient(Message message, LinkedBlockingQueue<Message> outputQueue);
}
