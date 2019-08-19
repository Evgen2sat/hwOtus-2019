package ru.otus.hw15.controllers;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.otus.hw15.dto.User;
import ru.otus.hw15.messageSystem.Address;
import ru.otus.hw15.messageSystem.Message;
import ru.otus.hw15.messageSystem.MessageSystem;
import ru.otus.hw15.messageSystem.MessageSystemContext;
import ru.otus.hw15.messageSystem.message.MsgCreateUser;

@Controller
public class AdminController implements FrontendService {

    private final MessageSystemContext messageSystemContext;
    private final Address address;

    private final SimpMessagingTemplate simpMessagingTemplate;

    public AdminController(MessageSystemContext messageSystemContext, Address address, SimpMessagingTemplate simpMessagingTemplate) {
        this.messageSystemContext = messageSystemContext;
        this.address = address;
        this.simpMessagingTemplate = simpMessagingTemplate;

        init();
    }

    @GetMapping({"/"})
    public String index(Model model) {
        return "index.html";
    }

    @GetMapping({"/admin"})
    public String adminPage(Model model) {
        return "users.html";
    }

    @MessageMapping("/admin/users")
    @Override
    public void createUser(User user) {
        Message msgCreateUser = new MsgCreateUser(getAddress(), messageSystemContext.getDbAddress(), user);
        messageSystemContext.getMessageSystem().sendMessage(msgCreateUser);
    }

    @Override
    public void accept(User user) {
        simpMessagingTemplate.convertAndSend("/admin/users", new Gson().toJson(user));
    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public void init() {
        messageSystemContext.getMessageSystem().addAddresse(this);
        messageSystemContext.setFrontendAddress(address);
    }

    @Override
    public MessageSystem getMS() {
        return messageSystemContext.getMessageSystem();
    }
}
