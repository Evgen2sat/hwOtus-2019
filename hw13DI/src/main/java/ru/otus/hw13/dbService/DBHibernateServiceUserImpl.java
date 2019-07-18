package ru.otus.hw13.dbService;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.springframework.stereotype.Service;
import ru.otus.hw13.Main;
import ru.otus.hw13.cache.CacheEngine;
import ru.otus.hw13.cache.CacheEngineImpl;
import ru.otus.hw13.dbService.DBService;
import ru.otus.hw13.dto.User;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Service
public class DBHibernateServiceUserImpl implements DBService<User> {
    private final SessionFactory sessionFactory;
//    private final CacheEngine<Long, User> cacheEngine;
//
    private CacheEngine<Long, User> cacheEngine = new CacheEngineImpl<>(3, 10000, false);

    public DBHibernateServiceUserImpl() {
        this.sessionFactory = new MetadataSources(Main.getStandardServiceRegistry())
                                .buildMetadata()
                                .buildSessionFactory();
    }


//    public DBHibernateServiceUserImpl(StandardServiceRegistry standardServiceRegistry) {
//        this.sessionFactory = new MetadataSources(standardServiceRegistry)
//                                        .buildMetadata()
//                                        .buildSessionFactory();
//
//        cacheEngine = new CacheEngineImpl<>(3, 10000, false);
//    }

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
}
