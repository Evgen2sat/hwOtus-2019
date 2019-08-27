package ru.otus.hw16.messageSystem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw16.messageSystem.message.Message;

import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.*;

public class MessageSystemImpl implements MessageSystem {

    private static Logger logger = LoggerFactory.getLogger(MessageSystemImpl.class);

    private ConcurrentMap<Socket, LinkedBlockingQueue<Message>> messageSocketMap = new ConcurrentHashMap<>();
    private ConcurrentMap<MessageType, ConcurrentMap<Integer, Socket>> registeredClients = new ConcurrentHashMap<>();

    @Override
    public void addSocket(Socket socket) {
        messageSocketMap.put(socket, new LinkedBlockingQueue<>());

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() -> processMsgSocketInput(socket));
        executorService.shutdown();
    }

    @Override
    public void sendMessage(Socket socket, Message message) {
        messageSocketMap.get(socket).add(message);
    }

    @Override
    public void registerSocketClient(Message message, Socket socket) {
        if(!registeredClients.containsKey(message.getType())) {
            ConcurrentMap<Integer, Socket> map = new ConcurrentHashMap<>();
            map.put(message.getAddress(), socket);
            registeredClients.put(message.getType(), map);
        } else {
            registeredClients.get(message.getType()).put(message.getAddress(), socket);
        }

        System.out.println("Зарегистрирован клиент: " + message.getType());
    }

    private void processMsgSocketInput(Socket socket) {
        LinkedBlockingQueue<Message> messages = messageSocketMap.get(socket);
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Message msg = messages.take();
//                System.out.println("Сообщение от фронтенда: " + msg);
                System.out.println("Сообщение от фронтенда: " + msg.getMsg());
                if(msg.getType() == MessageType.TO_DB) {
                    System.out.println("Отправляем в БД");
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(registeredClients.get(MessageType.REGISTER_DB).get(msg.getAddress()).getOutputStream());
                    System.out.println("Получил objectOutputStream");
                    objectOutputStream.writeObject(msg.getMsg());
                    System.out.println("Отправил в objectOutputStream");
                }
            }
        } catch (Exception e) {
            logger.error("error", e);
        }
    }
}
