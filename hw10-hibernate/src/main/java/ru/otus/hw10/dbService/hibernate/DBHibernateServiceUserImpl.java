package ru.otus.hw10.dbService.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import ru.otus.hw10.dbService.DBService;
import ru.otus.hw10.dto.Address;
import ru.otus.hw10.dto.User;
import ru.otus.hw10.tools.ReflectionHelper;

public class DBHibernateServiceUserImpl implements DBService<User> {
    private final SessionFactory sessionFactory;


    public DBHibernateServiceUserImpl(StandardServiceRegistry standardServiceRegistry) {
        sessionFactory = new MetadataSources(standardServiceRegistry)
                                            //.addAnnotatedClass(User.class)
                                            //.addAnnotatedClass(Address.class)
                                            .getMetadataBuilder()
                                            .build()
                                            .getSessionFactoryBuilder()
                                            .build();
    }

    @Override
    public void create(User data) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            session.save(data);
            session.getTransaction().commit();
        }
    }

    @Override
    public void update(User data) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            session.update(data);
            session.getTransaction().commit();
        }
    }

    @Override
    public User getItem(long id, Class<User> clazz) {
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
