package ru.otus.hw15.frontendService;

import ru.otus.hw15.dto.User;
import ru.otus.hw15.messageSystem.Address;
import ru.otus.hw15.messageSystem.Message;
import ru.otus.hw15.messageSystem.MessageSystem;
import ru.otus.hw15.messageSystem.MessageSystemContext;
import ru.otus.hw15.messageSystem.message.MsgCreateUser;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FrontendServiceImpl implements FrontendService {

    private final MessageSystemContext messageSystemContext;
    private final Address address;

    private final ArrayBlockingQueue<User> createdUsers = new ArrayBlockingQueue<>(10);

    public FrontendServiceImpl(MessageSystemContext messageSystemContext, Address address){
        this.messageSystemContext = messageSystemContext;
        this.address = address;
    }

    @Override
    public void createUser(User user) {
        Message msgCreateUser = new MsgCreateUser(getAddress(), messageSystemContext.getDbAddress(), user);
        messageSystemContext.getMessageSystem().sendMessage(msgCreateUser);
    }

    @Override
    public void init() {
        messageSystemContext.getMessageSystem().addAddresse(this);
        messageSystemContext.setFrontendAddress(address);
    }

    @Override
    public void accept(User user) {
        createdUsers.add(user);
    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public MessageSystem getMS() {
        return messageSystemContext.getMessageSystem();
    }

    public ArrayBlockingQueue<User> getCreatedUsers() {
        return createdUsers;
    }
}
