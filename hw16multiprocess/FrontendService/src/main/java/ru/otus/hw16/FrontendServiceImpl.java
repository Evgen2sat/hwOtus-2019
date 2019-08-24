package ru.otus.hw16;

import com.google.gson.Gson;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import ru.otus.hw16.dto.User;
import ru.otus.hw16.messageSystem.*;
import ru.otus.hw16.messageSystem.message.MsgCreateUser;
import ru.otus.hw16.messageSystem.message.MsgGetAllUsers;

import java.util.List;

public class FrontendServiceImpl implements FrontendService {

    private final MessageSystemContext messageSystemContext;
    private final Address address;
    private final SimpMessagingTemplate simpMessagingTemplate;

    public FrontendServiceImpl(MessageSystemContext messageSystemContext, Address address, SimpMessagingTemplate simpMessagingTemplate){
        this.messageSystemContext = messageSystemContext;
        this.address = address;
        this.simpMessagingTemplate = simpMessagingTemplate;
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
        simpMessagingTemplate.convertAndSend("/admin/users", new Gson().toJson(users));
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
    public void getAllUsers() {
        Message msgGetAllUsers = new MsgGetAllUsers(getAddress(), messageSystemContext.getDbAddress());
        messageSystemContext.getMessageSystem().sendMessage(msgGetAllUsers);
    }
}
