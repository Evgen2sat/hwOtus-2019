package ru.otus.hw10.dbService.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import ru.otus.hw10.dbService.DBService;
import ru.otus.hw10.dto.Account;

public class DBHibernateServiceAccountImpl implements DBService<Account> {
    private final SessionFactory sessionFactory;


    public DBHibernateServiceAccountImpl(StandardServiceRegistry standardServiceRegistry) {
//        sessionFactory = new MetadataSources(standardServiceRegistry)
//                .addAnnotatedClass(Account.class)
//                .getMetadataBuilder()
//                .build()
//                .getSessionFactoryBuilder()
//                .build();

        this.sessionFactory = new MetadataSources(standardServiceRegistry)
                .buildMetadata()
                .buildSessionFactory();
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

            return session.get(clazz, id);
        }
    }

    @Override
    public void createTable() {
        throw new UnsupportedOperationException();
    }

}
