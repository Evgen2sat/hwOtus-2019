package ru.otus.hw15.controllers;

import com.google.gson.Gson;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.hw15.dbService.DBService;
import ru.otus.hw15.dto.User;

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
}
