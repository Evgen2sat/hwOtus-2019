package ru.otus.hw15.controllers;

import com.google.gson.Gson;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.otus.hw15.MessageSystem;
import ru.otus.hw15.dto.User;

import java.util.concurrent.atomic.AtomicReference;

@Controller
public class AdminController {

    private final MessageSystem<User> messageSystem;

    public AdminController(MessageSystem<User> messageSystem) {
        this.messageSystem = messageSystem;
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
    @SendTo("/admin/users")
    public String createUser(User message) throws Exception {

        messageSystem.createItem(message);

        AtomicReference<User> user = new AtomicReference<>();

        Thread t1 = new Thread(() -> {
            while (true) {
                User createdItem = messageSystem.getCreatedItem();
                if(createdItem != null) {
                    user.set(createdItem);
                    break;
                }
            }
        });

        t1.start();
        t1.join();

        return new Gson().toJson(user.get());
    }
}
