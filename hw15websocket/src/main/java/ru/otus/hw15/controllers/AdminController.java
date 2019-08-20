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
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final LinkedBlockingQueue<User> createdUsers;
    private final LinkedBlockingQueue<List<User>> allUsersQueue;
    private final ExecutorService createdUsersExecutorService = Executors.newSingleThreadExecutor();
    private final ExecutorService allUsersExecutorService = Executors.newSingleThreadExecutor();

    public AdminController(FrontendService frontendService, SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.frontendService = frontendService;
        this.createdUsers = this.frontendService.getCreatedUsers();
        this.allUsersQueue = this.frontendService.getAllUsersQueue();

        createdUsersExecutorService.execute(this::accept);
        createdUsersExecutorService.shutdown();

        allUsersExecutorService.execute(this::acceptAllUsers);
        allUsersExecutorService.shutdown();
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

    private void accept() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                User createdUser = createdUsers.take();
                simpMessagingTemplate.convertAndSend("/admin/users", new Gson().toJson(createdUser));
            } catch (Exception e) {
                logger.error("error", e);
            }
        }
    }

    private void acceptAllUsers() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                List<User> allUsers = allUsersQueue.take();
                simpMessagingTemplate.convertAndSend("/admin/all-users", new Gson().toJson(allUsers));
            } catch (Exception e) {
                logger.error("error", e);
            }
        }
    }
}
