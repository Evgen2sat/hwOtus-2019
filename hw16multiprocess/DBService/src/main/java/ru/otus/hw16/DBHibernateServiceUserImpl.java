package ru.otus.hw16;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.otus.hw16.cache.CacheEngine;
import ru.otus.hw16.dto.User;
import ru.otus.hw16.messageSystem.Address;
import ru.otus.hw16.messageSystem.DBService;
import ru.otus.hw16.messageSystem.MessageSystem;
import ru.otus.hw16.messageSystem.MessageSystemContext;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class DBHibernateServiceUserImpl implements DBService<User> {
    private final SessionFactory sessionFactory;
    private final CacheEngine<Long, User> cacheEngine;
//    private final MessageSystemContext messageSystemContext;
    private final Address address;
    private Socket clientSocket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public DBHibernateServiceUserImpl(CacheEngine<Long, User> cacheEngine, /*MessageSystemContext messageSystemContext,*/ SessionFactory sessionFactory, Address address) {
        this.sessionFactory = sessionFactory;

        this.cacheEngine = cacheEngine;
//        this.messageSystemContext = messageSystemContext;
        this.address = address;
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
    public Address getAddress() {
        return address;
    }

    @Override
    public void init() {
//        messageSystemContext.getMessageSystem().addAddresse(this);
//        messageSystemContext.setDbAddress(address);
    }

    @Override
    public MessageSystem getMS() {
//        return messageSystemContext.getMessageSystem();
        return null;
    }

    @Override
    public void startConnection(String host, int port) throws IOException {
        clientSocket = new Socket(host, port);
        out = new ObjectOutputStream(clientSocket.getOutputStream());
        in = new ObjectInputStream(clientSocket.getInputStream());
    }

    @Override
    public void stopConnection() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }
}
