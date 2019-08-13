package ru.otus.hw15.controllers;

import com.google.gson.Gson;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.util.HtmlUtils;
import ru.otus.hw15.dbService.DBService;
import ru.otus.hw15.dto.User;

@Controller
public class AdminController {

    private final DBService<User> dbService;

    public AdminController(DBService<User> dbService) {
        this.dbService = dbService;
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

        User user = new User();
        user.setName(HtmlUtils.htmlEscape(message.getName()));
        user.setAge(message.getAge());

        return new Gson().toJson(dbService.getItem(dbService.create(user), User.class));
    }
}
