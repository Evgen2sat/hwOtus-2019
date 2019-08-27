package ru.otus.hw16.messageSystem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw16.messageSystem.message.Message;

import java.net.Socket;
import java.util.concurrent.*;

public class MessageSystemImpl implements MessageSystem {

    private static Logger logger = LoggerFactory.getLogger(MessageSystemImpl.class);

    private ConcurrentMap<Socket, LinkedBlockingQueue<Message>> messageSocketMap = new ConcurrentHashMap<>();

    @Override
    public void addSocket(Socket socket) {
        messageSocketMap.put(socket, new LinkedBlockingQueue<>());

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() -> processMsgSocketInput(socket));
        executorService.shutdown();
    }

    @Override
    public void sendMessage(Message message) {

    }

    private void processMsgSocketInput(Socket socket) {
        LinkedBlockingQueue<Message> messages = messageSocketMap.get(socket);
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Message msg = messages.take();

            }
        } catch (Exception e) {
            logger.error("error", e);
        }
    }
}
