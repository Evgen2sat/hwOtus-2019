package ru.otus.hw16.messageSystem.message;

import ru.otus.hw16.messageSystem.ActionType;
import ru.otus.hw16.messageSystem.MessageType;
import ru.otus.hw16.messageSystem.SocketClient;

import java.io.Serializable;
import java.net.Socket;

public class Message implements Serializable {
    private String msg;
    private int address;
    private MessageType type;
    private ActionType actionType;

    public Message(String msg, int address, MessageType type, ActionType actionType) {
        this.msg = msg;
        this.address = address;
        this.type = type;
        this.actionType = actionType;
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

    public ActionType getActionType() {
        return actionType;
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }
}
