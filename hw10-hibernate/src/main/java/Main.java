import org.hibernate.HibernateException;
import org.hibernate.Metamodel;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.boot.registry.internal.StandardServiceRegistryImpl;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.otus.hw10.dbService.hibernate.DBHibernateServiceUserImpl;
import ru.otus.hw10.dto.Account;
import ru.otus.hw10.dto.User;

import javax.persistence.metamodel.EntityType;

import java.util.Map;

public class Main {

    private static final StandardServiceRegistry standardServiceRegistry;

//    private static final SessionFactory ourSessionFactory;

    static {
        try {
            Configuration configuration = new Configuration();
            configuration.configure("hibernate.cfg.xml");

            standardServiceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties())
                    .build();

//            Metadata metadata = new MetadataSources(standardServiceRegistry)
//                    .addAnnotatedClass(User.class)
//                    .addAnnotatedClass(Account.class)
//                    .getMetadataBuilder()
//                    .build();
//
//            ourSessionFactory = metadata.getSessionFactoryBuilder().build();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static void main(final String[] args) throws Exception {

        User person = new User();
        person.setName("Ivan");
        person.setAge(10);

        DBHibernateServiceUserImpl dbHibernateServiceUser = new DBHibernateServiceUserImpl(standardServiceRegistry);
        dbHibernateServiceUser.create(person);

        User item = dbHibernateServiceUser.getItem(1, User.class);
        System.out.println(item);
        item.setName("ChangedUser");
        dbHibernateServiceUser.update(item);
        System.out.println(dbHibernateServiceUser.getItem(1, User.class));
    }
}