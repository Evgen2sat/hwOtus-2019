package ru.otus.hw15.config;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import ru.otus.hw15.Main;
import ru.otus.hw15.cache.CacheEngine;
import ru.otus.hw15.cache.CacheEngineImpl;
import ru.otus.hw15.controllers.AdminController;
import ru.otus.hw15.dbService.DBHibernateServiceUserImpl;
import ru.otus.hw15.dbService.DBService;
import ru.otus.hw15.dto.User;
import ru.otus.hw15.messageSystem.Address;
import ru.otus.hw15.messageSystem.MessageSystem;
import ru.otus.hw15.messageSystem.MessageSystemContext;
import ru.otus.hw15.messageSystem.MessageSystemImpl;

@Configuration
public class Config {
    @Bean
    public CacheEngine<Long, User> initUserCache() {
        return new CacheEngineImpl<>(3, 10000, false);
    }

    @Bean
    public MessageSystem initMessageSystem() {
        MessageSystemImpl messageSystem = new MessageSystemImpl();
        messageSystem.init();

        return messageSystem;
    }

    @Bean
    public DBService<User> initDBService(CacheEngine<Long, User> cacheEngine, MessageSystemContext messageSystemContext) {
        StandardServiceRegistry standardServiceRegistry = new StandardServiceRegistryBuilder()
                .configure()
                .build();

        SessionFactory sessionFactory = new MetadataSources(standardServiceRegistry)
                .buildMetadata()
                .buildSessionFactory();

        DBHibernateServiceUserImpl dbHibernateServiceUser = new DBHibernateServiceUserImpl(cacheEngine, messageSystemContext, sessionFactory, new Address());
        dbHibernateServiceUser.init();

        return dbHibernateServiceUser;
    }

    @Bean
    public MessageSystemContext initMessageSystemContext(MessageSystem messageSystem) {
        return new MessageSystemContext(messageSystem);
    }

    @Bean
    public Address initAddress() {
        return new Address();
    }

}
