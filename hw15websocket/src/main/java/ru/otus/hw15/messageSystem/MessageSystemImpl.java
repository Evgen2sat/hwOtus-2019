package ru.otus.hw15.messageSystem;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.*;

public class MessageSystemImpl implements MessageSystem {

    private ExecutorService inputMessageExecutorService = Executors.newSingleThreadExecutor();

    private ConcurrentMap<Address, Addresse> addresseMap = new ConcurrentHashMap<>();
    private ConcurrentMap<Address, LinkedBlockingQueue<Message>> messageMap = new ConcurrentHashMap<>();

    @Override
    public void addAddresse(Addresse addresse) {
        addresseMap.put(addresse.getAddress(), addresse);
        messageMap.put(addresse.getAddress(), new LinkedBlockingQueue<>());
    }

    @Override
    public void sendMessage(Message message) {
        messageMap.get(message.getTo()).add(message);
    }

    @Override
    public void init() {
        inputMessageExecutorService.execute(this::processMsgInput);

        inputMessageExecutorService.shutdown();
    }

    private void processMsgInput() {
        while (!Thread.currentThread().isInterrupted()) {
            for (var addresse : addresseMap.entrySet()) {
                LinkedBlockingQueue<Message> messages = messageMap.get(addresse.getKey());
                if (messages != null && !messages.isEmpty()) {
                    try {
                        Message msg = messages.take();
                        msg.execute(addresse.getValue());
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }
    }
}
