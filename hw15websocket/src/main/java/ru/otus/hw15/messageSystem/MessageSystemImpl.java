package ru.otus.hw15.messageSystem;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.*;

public class MessageSystemImpl implements MessageSystem {

    private ExecutorService inputMessageExecutorService = Executors.newSingleThreadExecutor();

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
            Thread.currentThread().interrupt();
        }



    }
}
