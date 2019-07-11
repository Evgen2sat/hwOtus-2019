package ru.otus.hw11.dbService.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import ru.otus.hw11.cache.CacheEngine;
import ru.otus.hw11.cache.CacheEngineImpl;
import ru.otus.hw11.dbService.DBService;
import ru.otus.hw11.dto.Account;

public class DBHibernateServiceAccountImpl implements DBService<Account> {
    private final SessionFactory sessionFactory;

    private final CacheEngine<Long, Account> cacheEngine;

    public DBHibernateServiceAccountImpl(StandardServiceRegistry standardServiceRegistry) {
        this.sessionFactory = new MetadataSources(standardServiceRegistry)
                .buildMetadata()
                .buildSessionFactory();

        cacheEngine = new CacheEngineImpl<>(3, 10000, false);
    }

    @Override
    public void create(Account data) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            session.save(data);
            session.getTransaction().commit();
        }
    }

    @Override
    public void update(Account data) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            session.update(data);
            session.getTransaction().commit();
        }
    }

    @Override
    public Account getItem(long id, Class<Account> clazz) {
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
