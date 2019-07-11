package ru.otus.hw11.dbService.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import ru.otus.hw11.cache.CacheEngine;
import ru.otus.hw11.cache.CacheEngineImpl;
import ru.otus.hw11.dbService.DBService;
import ru.otus.hw11.dto.User;

public class DBHibernateServiceUserImpl implements DBService<User> {
    private final SessionFactory sessionFactory;
    private final CacheEngine<Long, User> cacheEngine;


    public DBHibernateServiceUserImpl(StandardServiceRegistry standardServiceRegistry) {
        this.sessionFactory = new MetadataSources(standardServiceRegistry)
                                        .buildMetadata()
                                        .buildSessionFactory();

        cacheEngine = new CacheEngineImpl<>(3, 10000, false);
    }

    @Override
    public void create(User data) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            session.save(data);
            session.getTransaction().commit();

            cacheEngine.put(data.getId(), data);
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
            session.beginTransaction();

            return cacheEngine.get(id, userId -> {
                session.beginTransaction();
                return session.get(clazz, userId);
            });
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
}
