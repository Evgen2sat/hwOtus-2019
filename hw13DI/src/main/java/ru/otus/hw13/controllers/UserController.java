package ru.otus.hw13.controllers;

import com.google.gson.Gson;
import org.springframework.web.bind.annotation.*;
import ru.otus.hw13.dbService.DBService;
import ru.otus.hw13.dto.User;

import java.util.List;

@RestController
public class UserController {

    private final DBService<User> dbService;

    public UserController(DBService<User> dbService) {
        this.dbService = dbService;
    }

    @GetMapping(value = "/admin/users", produces = "application/json")
    @ResponseBody
    public String getUsers() {
        List<User> items = dbService.getItems();

        return new Gson().toJson(items);
    }

    @PostMapping("/admin/users")
    public void createUser(@RequestBody User user) {
        dbService.create(user);
    }
}
