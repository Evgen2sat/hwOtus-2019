package ru.otus.hw16;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw16.dto.User;
import ru.otus.hw16.messageSystem.MessageSystem;
import ru.otus.hw16.messageSystem.MessageType;
import ru.otus.hw16.messageSystem.message.Message;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketServerHandler extends Thread {
    private static Logger logger = LoggerFactory.getLogger(SocketServerHandler.class);

    private final Socket clientSocket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private final MessageSystem messageSystem;

    public SocketServerHandler(Socket clientSocket, MessageSystem messageSystem) {
        this.clientSocket = clientSocket;
        this.messageSystem = messageSystem;
        this.messageSystem.addSocket(clientSocket);
        System.out.println("Подключен клиент: " + this.clientSocket.toString());
    }

    @Override
    public void run() {
        try {
            outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            inputStream = new ObjectInputStream(clientSocket.getInputStream());
            while (!Thread.currentThread().isInterrupted()) {
                Message msg = (Message) inputStream.readObject();
                outputStream.writeObject(msg);

                if(msg.getType() == MessageType.REGISTER_FRONTEND || msg.getType() == MessageType.REGISTER_DB) {
                    messageSystem.registerSocketClient(msg, clientSocket);
                } else {
                    //                System.out.println("Сообщение от клиента: " + msg);
                    messageSystem.sendMessage(clientSocket, msg);
                }
            }


        } catch (Exception e) {
            logger.error("error", e);
            try {
                inputStream.close();
                outputStream.close();
                clientSocket.close();
            } catch (Exception ex) {
                logger.error("error", ex);
            }
        } finally {
            try {
                inputStream.close();
                outputStream.close();
                clientSocket.close();
            } catch (Exception e) {
                logger.error("error", e);
            }
        }
    }
}
