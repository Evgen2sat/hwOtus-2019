package ru.otus.hw15.controllers;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.otus.hw15.dto.User;
import ru.otus.hw15.messageSystem.Message;
import ru.otus.hw15.messageSystem.MessageClient;
import ru.otus.hw15.messageSystem.MessageSystem;

@Controller
public class AdminController implements MessageClient<User> {

    private final MessageSystem messageSystem;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    public AdminController(MessageSystem messageSystem) {
        this.messageSystem = messageSystem;
        this.messageSystem.setFrontendClient(this);
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
    public void createUser(User message) throws InterruptedException {
        messageSystem.sendMessage(messageSystem.createMsgToDatabase(message));
    }

    @Override
    public void accept(Message<User> message) {
        simpMessagingTemplate.convertAndSend("/admin/users", new Gson().toJson(message.getData()));
    }
}
