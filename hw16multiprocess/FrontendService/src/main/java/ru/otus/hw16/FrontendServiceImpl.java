package ru.otus.hw16;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import ru.otus.hw16.controllers.AdminController;
import ru.otus.hw16.dto.User;
import ru.otus.hw16.messageSystem.*;
import ru.otus.hw16.messageSystem.message.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FrontendServiceImpl implements FrontendService {

    private static Logger logger = LoggerFactory.getLogger(AdminController.class);

    private final SimpMessagingTemplate simpMessagingTemplate;
    private Socket clientSocket;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    public FrontendServiceImpl(SimpMessagingTemplate simpMessagingTemplate){
        this.simpMessagingTemplate = simpMessagingTemplate;

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(this::readInputstream);
        executorService.shutdown();
    }

    @Override
    public void createUser(User user) {
        try {
            String jsonObject = new Gson().toJson(user);
            Message msg = new Message(jsonObject, FrontendServiceMain.ADDRESS, MessageType.TO_DB, ActionType.CREATE_USER);
            out.writeObject(msg);
        } catch (IOException e) {
            logger.error("error", e);
        }
    }

    @Override
    public void accept(List<User> users) {
        simpMessagingTemplate.convertAndSend("/admin/users", new Gson().toJson(users));
    }

    @Override
    public void getAllUsers() {
        try {
            Message msg = new Message(null, FrontendServiceMain.ADDRESS, MessageType.TO_DB, ActionType.GET_ALL_USERS);
            out.writeObject(msg);
        } catch (IOException e) {
            logger.error("error", e);
        }
    }

    @Override
    public void startConnection(String host, int port) throws IOException {
        clientSocket = new Socket(host, port);
        this.out = new ObjectOutputStream(clientSocket.getOutputStream());
        this.in = new ObjectInputStream(clientSocket.getInputStream());

        Message message = new Message(null, FrontendServiceMain.ADDRESS, MessageType.REGISTER_FRONTEND, null);
        this.out.writeObject(message);
    }

    @Override
    public void stopConnection() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }

    private void readInputstream() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Message msg = (Message) in.readObject();
                accept(new Gson().fromJson(msg.getMsg(), new TypeToken<List<User>>(){}.getType()));
            } catch (Exception e) {
                logger.error("error", e);
            }
        }
    }
}
