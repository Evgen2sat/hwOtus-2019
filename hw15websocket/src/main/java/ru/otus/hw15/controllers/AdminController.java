package ru.otus.hw15.controllers;

import com.google.gson.Gson;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.util.HtmlUtils;
import ru.otus.hw15.MessageSystem;
import ru.otus.hw15.dbService.DBService;
import ru.otus.hw15.dto.User;

@Controller
public class AdminController {

    private final MessageSystem<User> messageSystem;

    public AdminController(MessageSystem<User> messageSystem) {
        this.messageSystem = messageSystem;

        Thread t1 = new Thread(() -> {
            while (true) {
               messageSystem.getCreatedItem().ifPresent(this::returnUser);
            }
        });

        t1.start();
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
//    @SendTo("/admin/users")
    public void createUser(User message) throws Exception {

        messageSystem.createItem(message);

//        return null;
//        return new Gson().toJson(dbService.getItem(dbService.create(message), User.class));
    }

    @SendTo("/admin/users")
    public String returnUser(User user) {
        return new Gson().toJson(user);
    }
}
