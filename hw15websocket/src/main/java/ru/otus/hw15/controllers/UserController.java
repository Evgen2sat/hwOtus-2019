package ru.otus.hw15.controllers;

import com.google.gson.Gson;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.*;
import ru.otus.hw15.dto.User;

import java.util.List;

@RestController
public class UserController {

//    private final DBService<User> dbService;
//
//    public UserController(DBService<User> dbService) {
//        this.dbService = dbService;
//    }

    @GetMapping(value = "/admin/users", produces = "application/json")
    @ResponseBody
    public String getUsers() {
//        List<User> items = dbService.getItems();
//
//        return new Gson().toJson(items);

        return new Gson().toJson("TEST");
    }

    @PostMapping("/admin/users")
    public void createUser(@RequestBody User user) {
//        dbService.create(user);
    }
}
