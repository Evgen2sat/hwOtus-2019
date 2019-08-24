package ru.otus.hw15.controllers;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.otus.hw15.dto.User;
import ru.otus.hw15.frontendService.FrontendService;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

@Controller
public class AdminController {

    private static Logger logger = LoggerFactory.getLogger(AdminController.class);

    private final FrontendService frontendService;

    public AdminController(FrontendService frontendService) {
        this.frontendService = frontendService;
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
    public void createUser(User user) {
        frontendService.createUser(user);
    }

    @MessageMapping("/admin/all-users")
    public void getAllUsers() {
        frontendService.getAllUsers();
    }
}
