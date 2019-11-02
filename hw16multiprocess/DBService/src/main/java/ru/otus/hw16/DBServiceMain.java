package ru.otus.hw16;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw16.cache.CacheEngineImpl;
import ru.otus.hw16.dto.User;

import java.io.IOException;

public class DBServiceMain {

    private static Logger logger = LoggerFactory.getLogger(DBServiceMain.class);

    public static int ADDRESS;

    public static void main(String[] args) {

        for(String arg : args) {
            if(arg.startsWith("-address")) {
                ADDRESS = Integer.valueOf(arg.split("=")[1]);
                break;
            }
        }

        StandardServiceRegistry standardServiceRegistry = new StandardServiceRegistryBuilder()
                .configure()
                .build();

        SessionFactory sessionFactory = new MetadataSources(standardServiceRegistry)
                .buildMetadata()
                .buildSessionFactory();

        CacheEngineImpl<Long, User> cacheEngine = new CacheEngineImpl<>(3, 10000, false);

        DBHibernateServiceUserImpl dbHibernateServiceUser = new DBHibernateServiceUserImpl(cacheEngine, sessionFactory);
        try {
            dbHibernateServiceUser.startConnection("127.0.0.1", 4444);
        } catch (IOException e) {
            logger.error("error", e);
        }
    }
}
