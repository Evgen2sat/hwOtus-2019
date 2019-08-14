package ru.otus.hw15;

import org.springframework.stereotype.Service;
import ru.otus.hw15.dbService.DBService;
import ru.otus.hw15.dto.User;

import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class MessageSystemImpl implements MessageSystem<User> {
    private Queue<User> frontendQueue = new ConcurrentLinkedQueue<>();
    private Queue<User> dbServiceQueue = new ConcurrentLinkedQueue<>();

    public MessageSystemImpl(DBService<User> dbService) {
        Thread frontendQueueThread = new Thread(() -> {
            while (true) {
                User user = frontendQueue.poll();

                if(user != null) {
                    dbService.create(user);
                }
            }
        });

        Thread dbServiceQueueThread = new Thread(() -> {
            while (true) {
                User createdUser = dbService.getCreatedItem();
                if(createdUser != null) {
                    dbServiceQueue.add(createdUser);
                }
            }
        });

        frontendQueueThread.start();
        dbServiceQueueThread.start();
    }

    @Override
    public void createItem(User user) {
        frontendQueue.add(user);
    }

    @Override
    public User getCreatedItem() {
        return dbServiceQueue.poll();
    }
}
