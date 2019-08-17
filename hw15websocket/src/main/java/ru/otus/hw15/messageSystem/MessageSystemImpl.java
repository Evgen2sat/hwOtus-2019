package ru.otus.hw15.messageSystem;

import ru.otus.hw15.dto.User;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MessageSystemImpl implements MessageSystem {

    private MessageClient frontendClient;
    private MessageClient databaseClient;

    private ArrayBlockingQueue<Message> inputMessageQueue = new ArrayBlockingQueue<>(10);
    private ArrayBlockingQueue<Message> messageForFrontendQueue = new ArrayBlockingQueue<>(10);
    private ArrayBlockingQueue<Message> messageForDatabaseQueue = new ArrayBlockingQueue<>(10);

    private ExecutorService inputMessageExecutorService = Executors.newSingleThreadExecutor();
    private ExecutorService outputMessageForFrontendExecutorService = Executors.newSingleThreadExecutor();
    private ExecutorService outputMessageDatabaseExecutorService = Executors.newSingleThreadExecutor();


    @Override
    public void sendMessage(Message message) throws InterruptedException {
        inputMessageQueue.put(message);
    }

    @Override
    public Message<User> createMsgToDatabase(User data) {
        MessageToDatabase messageToDatabase = new MessageToDatabase();
        messageToDatabase.setData(data);
        messageToDatabase.setQueueTo(messageForDatabaseQueue);

        return messageToDatabase;
    }

    @Override
    public Message<User> createMsgToFrontend(User data) {
        MessageToFrontend messageToFrontend = new MessageToFrontend();
        messageToFrontend.setData(data);
        messageToFrontend.setQueueTo(messageForFrontendQueue);

        return messageToFrontend;
    }

    @Override
    public void init() {
        inputMessageExecutorService.execute(this::processMsgInput);


        inputMessageExecutorService.shutdown();
    }

    private void processMsgInput() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Message msg = inputMessageQueue.take();
                System.out.println("new message: " + msg);
                msg.getQueueTo().put(msg);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private void processMsgOutput(ArrayBlockingQueue<Message> queue, MessageClient client) {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Message msg = queue.take();
                client.accept(msg);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    @Override
    public void setDatabaseClient(MessageClient messageClient) {
        this.databaseClient = messageClient;

        outputMessageDatabaseExecutorService.execute(() -> processMsgOutput(messageForDatabaseQueue, this.databaseClient));
        outputMessageDatabaseExecutorService.shutdown();
    }

    @Override
    public void setFrontendClient(MessageClient messageClient) {
        this.frontendClient = messageClient;

        outputMessageForFrontendExecutorService.execute(() -> processMsgOutput(messageForFrontendQueue, this.frontendClient));
        outputMessageForFrontendExecutorService.shutdown();
    }
}
