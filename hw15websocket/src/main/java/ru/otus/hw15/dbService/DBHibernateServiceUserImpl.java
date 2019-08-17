package ru.otus.hw15.dbService;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.springframework.stereotype.Service;
import ru.otus.hw15.Main;
import ru.otus.hw15.cache.CacheEngine;
import ru.otus.hw15.dto.User;
import ru.otus.hw15.messageSystem.Message;
import ru.otus.hw15.messageSystem.MessageSystem;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Service
public class DBHibernateServiceUserImpl implements DBService<User> {
    private final SessionFactory sessionFactory;
    private final CacheEngine<Long, User> cacheEngine;
    private final MessageSystem messageSystem;

    public DBHibernateServiceUserImpl(CacheEngine<Long, User> cacheEngine, MessageSystem messageSystem) {
        this.sessionFactory = new MetadataSources(Main.getStandardServiceRegistry())
                                .buildMetadata()
                                .buildSessionFactory();

        this.cacheEngine = cacheEngine;
        this.messageSystem = messageSystem;
        this.messageSystem.setDatabaseClient(this);
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
    public void accept(Message<User> message) throws InterruptedException {
        messageSystem.sendMessage(messageSystem.createMsgToFrontend(create(message.getData())));
    }
}
