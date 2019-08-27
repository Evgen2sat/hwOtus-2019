package ru.otus.hw16;

import com.google.gson.Gson;
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

public class FrontendServiceImpl implements FrontendService {

    private static Logger logger = LoggerFactory.getLogger(AdminController.class);

    private final SimpMessagingTemplate simpMessagingTemplate;
    private Socket clientSocket;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    public FrontendServiceImpl(SimpMessagingTemplate simpMessagingTemplate){
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @Override
    public void createUser(User user) {
//        Message msgCreateUser = new MsgCreateUser(getAddress(), messageSystemContext.getDbAddress(), user);
//        messageSystemContext.getMessageSystem().sendMessage(msgCreateUser);
        try {
//            Message msg = new Message(user.getName(), this);
            out.writeObject(user.getName());
        } catch (IOException e) {
            logger.error("error", e);
        }
    }

    @Override
    public void init() throws IOException {
//        messageSystemContext.getMessageSystem().addAddresse(this);
//        messageSystemContext.setFrontendAddress(address);
        this.out = new ObjectOutputStream(clientSocket.getOutputStream());
        this.in = new ObjectInputStream(clientSocket.getInputStream());
    }

    @Override
    public void accept(List<User> users) {
        simpMessagingTemplate.convertAndSend("/admin/users", new Gson().toJson(users));
    }

    @Override
    public void getAllUsers() {
//        Message msgGetAllUsers = new MsgGetAllUsers(getAddress(), messageSystemContext.getDbAddress());
//        messageSystemContext.getMessageSystem().sendMessage(msgGetAllUsers);
    }

    @Override
    public void startConnection(String host, int port) throws IOException {
        clientSocket = new Socket(host, port);
        this.out = new ObjectOutputStream(clientSocket.getOutputStream());
        this.in = new ObjectInputStream(clientSocket.getInputStream());

        Message message = new Message(null, 100, MessageType.REGISTER_FRONTEND);
        this.out.writeObject(message);
    }

    @Override
    public void stopConnection() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }
}
