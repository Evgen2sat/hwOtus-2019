package ru.otus.hw13;

import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.otus.hw13.dbService.DBService;
import ru.otus.hw13.dbService.hibernate.DBHibernateServiceUserImpl;
import ru.otus.hw13.dto.User;
import ru.otus.hw13.jetty.JettyServer;

public class Main {

    private static final StandardServiceRegistry standardServiceRegistry;

    static {
        try {
            standardServiceRegistry = new StandardServiceRegistryBuilder()
                    .configure()
                    .build();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static void main(final String[] args) throws Exception {
        try(DBService<User> dbHibernateServiceUser = new DBHibernateServiceUserImpl(standardServiceRegistry)) {
            for(int i = 0; i < 10; i++) {
                User person = new User();
                person.setName("user" + (i+1));
                person.setAge(i+1);
                dbHibernateServiceUser.create(person);
            }
        }

        JettyServer jettyServer = new JettyServer(standardServiceRegistry);
        jettyServer.start();
    }
}