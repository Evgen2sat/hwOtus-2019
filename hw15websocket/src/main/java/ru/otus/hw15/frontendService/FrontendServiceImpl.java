package ru.otus.hw15.frontendService;

import ru.otus.hw15.dto.User;
import ru.otus.hw15.messageSystem.Address;
import ru.otus.hw15.messageSystem.Message;
import ru.otus.hw15.messageSystem.MessageSystem;
import ru.otus.hw15.messageSystem.MessageSystemContext;
import ru.otus.hw15.messageSystem.message.MsgCreateUser;
import ru.otus.hw15.messageSystem.message.MsgGetAllUsers;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class FrontendServiceImpl implements FrontendService {

    private final MessageSystemContext messageSystemContext;
    private final Address address;

    private final LinkedBlockingQueue<User> createdUsers = new LinkedBlockingQueue<>();
    private final LinkedBlockingQueue<List<User>> allUsersQueue = new LinkedBlockingQueue<>();

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
    public void accept(List<User> users) {
        createdUsers.addAll(users);
    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public MessageSystem getMS() {
        return messageSystemContext.getMessageSystem();
    }

    @Override
    public LinkedBlockingQueue<User> getCreatedUsers() {
        return createdUsers;
    }

    @Override
    public void getAllUsers() {
        Message msgGetAllUsers = new MsgGetAllUsers(getAddress(), messageSystemContext.getDbAddress());
        messageSystemContext.getMessageSystem().sendMessage(msgGetAllUsers);
    }

    @Override
    public LinkedBlockingQueue<List<User>> getAllUsersQueue() {
        return allUsersQueue;
    }

    @Override
    public void acceptAllUsers(List<User> users) {
        allUsersQueue.add(users);
    }
}
