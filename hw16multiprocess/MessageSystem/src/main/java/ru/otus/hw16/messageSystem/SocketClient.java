package ru.otus.hw16.messageSystem;

import java.io.IOException;

public interface SocketClient {
    void startConnection(String host, int port) throws IOException;

    void stopConnection() throws IOException;
}
