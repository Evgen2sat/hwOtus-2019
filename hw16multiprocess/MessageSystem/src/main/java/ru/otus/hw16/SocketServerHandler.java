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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class SocketServerHandler extends Thread {
    private static Logger logger = LoggerFactory.getLogger(SocketServerHandler.class);

    private final Socket clientSocket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private final MessageSystem messageSystem;

    private LinkedBlockingQueue<Message> outputQueue = new LinkedBlockingQueue<>();

    public SocketServerHandler(Socket clientSocket, MessageSystem messageSystem) {
        this.clientSocket = clientSocket;
        this.messageSystem = messageSystem;
        this.messageSystem.addSocket(clientSocket);
        System.out.println("Подключен клиент: " + this.clientSocket.toString());

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(this::processMsgSocketOutput);
        executorService.shutdown();
    }

    @Override
    public void run() {
        try {
            outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            inputStream = new ObjectInputStream(clientSocket.getInputStream());
            while (!Thread.currentThread().isInterrupted()) {
                Message msg = (Message) inputStream.readObject();
                if(msg.getType() == MessageType.REGISTER_FRONTEND || msg.getType() == MessageType.REGISTER_DB) {
                    System.out.println("Отправляю сервис на регистрацию в MS");
                    messageSystem.registerSocketClient(msg, outputQueue);
                } else {
                    System.out.println("Отправляю сообщение в MS");
                    messageSystem.sendMessage(clientSocket, msg);
                }
            }
        } catch (Exception e) {
            logger.error("error", e);
            try {
                System.out.println("Закрываю потоки в SocketServerHandler после эксепшена");
                inputStream.close();
                outputStream.close();
                clientSocket.close();
            } catch (Exception ex) {
                logger.error("error", ex);
            }
        } finally {
            try {
                System.out.println("Закрываю потоки в SocketServerHandler");
                inputStream.close();
                outputStream.close();
                clientSocket.close();
            } catch (Exception e) {
                logger.error("error", e);
            }
        }
    }

    private void processMsgSocketOutput() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Message msg = outputQueue.take();
                System.out.println("Есть сообщение в очереди для отправки: " + msg.getMsg());
                outputStream.writeObject(msg.getMsg());
                System.out.println("Отправил сообщение в исходящий поток: " + msg.getMsg());
            }
        } catch (Exception e) {
            logger.error("error", e);
        }
    }
}
