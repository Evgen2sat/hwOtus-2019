package ru.otus.hw16.messageSystem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw16.messageSystem.message.Message;

import java.util.concurrent.*;

public class MessageSystemImpl implements MessageSystem {

    private static Logger logger = LoggerFactory.getLogger(MessageSystemImpl.class);

    private ConcurrentMap<Address, Addresse> addresseMap = new ConcurrentHashMap<>();
    private ConcurrentMap<Address, LinkedBlockingQueue<Message>> messageMap = new ConcurrentHashMap<>();
    private ConcurrentMap<Address, ExecutorService> executorServiceConcurrentMap = new ConcurrentHashMap<>();

    @Override
    public void addAddresse(Addresse addresse) {
        addresseMap.put(addresse.getAddress(), addresse);
        messageMap.put(addresse.getAddress(), new LinkedBlockingQueue<>());

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() -> processMsgInput(addresse.getAddress(), addresse));
        executorService.shutdown();

        executorServiceConcurrentMap.put(addresse.getAddress(), executorService);
    }

    @Override
    public void sendMessage(Message message) {
        messageMap.get(message.getTo()).add(message);
    }

    private void processMsgInput(Address address, Addresse addresse) {
        LinkedBlockingQueue<Message> messages = messageMap.get(address);
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Message msg = messages.take();
                msg.execute(addresse);
            }
        } catch (Exception e) {
            logger.error("error", e);
        }
    }
}
