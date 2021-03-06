package ru.otus.hw16;

import com.google.gson.Gson;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw16.cache.CacheEngine;
import ru.otus.hw16.dto.User;
import ru.otus.hw16.messageSystem.DBService;
import ru.otus.hw16.messageSystem.MessageSystem;
import ru.otus.hw16.messageSystem.MessageType;
import ru.otus.hw16.messageSystem.message.Message;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DBHibernateServiceUserImpl implements DBService<User> {
    private final SessionFactory sessionFactory;
    private final CacheEngine<Long, User> cacheEngine;
    private Socket clientSocket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private static Logger logger = LoggerFactory.getLogger(DBHibernateServiceUserImpl.class);

    public DBHibernateServiceUserImpl(CacheEngine<Long, User> cacheEngine, SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;

        this.cacheEngine = cacheEngine;
    }

    @Override
    public User create(User data) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            session.save(data);
            session.getTransaction().commit();

            cacheEngine.put(data.getId(), data);

            return data;
        }
    }

    @Override
    public void update(User data) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            session.update(data);
            session.getTransaction().commit();

            cacheEngine.put(data.getId(), data);
        }
    }

    @Override
    public User getItem(long id, Class<User> clazz) {
        try (Session session = sessionFactory.openSession()) {
            return cacheEngine.get(id, userId -> {
                session.beginTransaction();
                return session.get(clazz, userId);
            });
        }
    }

    @Override
    public List<User> getItems() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<User> cq = cb.createQuery(User.class);
            Root<User> rootEntry = cq.from(User.class);
            CriteriaQuery<User> all = cq.select(rootEntry);

            TypedQuery<User> allQuery = session.createQuery(all);
            return allQuery.getResultList();
        }
    }

    @Override
    public void createTable() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void close() throws Exception {
        cacheEngine.dispose();
    }

    @Override
    public void startConnection(String host, int port) throws IOException {
        clientSocket = new Socket(host, port);
        outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
        inputStream = new ObjectInputStream(clientSocket.getInputStream());

        Message message = new Message(null, DBServiceMain.ADDRESS, MessageType.REGISTER_DB, null);
        outputStream.writeObject(message);

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(this::readInputstream);
        executorService.shutdown();
    }

    @Override
    public void stopConnection() throws IOException {
        inputStream.close();
        outputStream.close();
        clientSocket.close();
    }

    private void readInputstream() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Message msg = (Message) inputStream.readObject();

                action(msg);
            } catch (Exception e) {
                logger.error("error", e);
            }
        }
    }

    private void action(Message message) throws IOException {
        switch (message.getActionType()) {
            case CREATE_USER:
                User user = new Gson().fromJson(message.getMsg(), User.class);
                create(user);
                outputStream.writeObject(new Message(new Gson().toJson(getItems()), DBServiceMain.ADDRESS, MessageType.TO_FRONTEND, null));
                break;
            case GET_ALL_USERS:
                outputStream.writeObject(new Message(new Gson().toJson(getItems()), DBServiceMain.ADDRESS, MessageType.TO_FRONTEND, null));
                break;
        }
    }
}
