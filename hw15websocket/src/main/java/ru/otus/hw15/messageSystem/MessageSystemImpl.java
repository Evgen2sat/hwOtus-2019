package ru.otus.hw15.messageSystem;

import ru.otus.hw15.dto.User;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.*;

public class MessageSystemImpl implements MessageSystem {


    private ArrayBlockingQueue<Message> inputMessageQueue = new ArrayBlockingQueue<>(10);

    private ExecutorService inputMessageExecutorService = Executors.newSingleThreadExecutor();

    private Map<Address, Addresse> addresseMap = new HashMap<>();
    private Map<Address, LinkedBlockingQueue<Message>> messageMap = new LinkedHashMap<>();

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
//        while (!Thread.currentThread().isInterrupted()) {
            for (var addresse : addresseMap.entrySet()) {
                LinkedBlockingQueue<Message> messages = messageMap.get(addresse.getKey());
                if(messages != null && !messages.isEmpty()) {
                    while (!Thread.currentThread().isInterrupted()) {
                        try {
                            Message msg = messages.take();
                            msg.execute(addresse.getValue());
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
            }
//        }
    }
}
