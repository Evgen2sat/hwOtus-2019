package ru.otus.hw16.messageSystem.message;

import ru.otus.hw16.messageSystem.MessageType;
import ru.otus.hw16.messageSystem.SocketClient;

import java.io.Serializable;
import java.net.Socket;

public class Message implements Serializable {
    private String msg;
    private int address;
    private MessageType type;

    public Message(String msg, int address, MessageType type) {
        this.msg = msg;
        this.address = address;
        this.type = type;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getAddress() {
        return address;
    }

    public void setAddress(int address) {
        this.address = address;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }
}
