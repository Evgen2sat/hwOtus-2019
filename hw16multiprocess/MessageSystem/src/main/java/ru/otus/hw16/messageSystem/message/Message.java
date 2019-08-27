package ru.otus.hw16.messageSystem.message;

import ru.otus.hw16.messageSystem.SocketClient;

import java.net.Socket;

public class Message {
    private Socket fromSocket;
    private String msg;
    private SocketClient fromSocketClient;

    public Message(String msg, Socket fromSocket) {
        this.msg = msg;
        this.fromSocket = fromSocket;
    }

    public Socket getFromSocket() {
        return fromSocket;
    }

    public void setFromSocket(Socket fromSocket) {
        this.fromSocket = fromSocket;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public SocketClient getFromSocketClient() {
        return fromSocketClient;
    }

    public void setFromSocketClient(SocketClient fromSocketClient) {
        this.fromSocketClient = fromSocketClient;
    }
}
