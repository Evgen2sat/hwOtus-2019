package ru.otus.hw12;

import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.otus.hw12.dbService.DBService;
import ru.otus.hw12.dbService.hibernate.DBHibernateServiceUserImpl;
import ru.otus.hw12.dto.Address;
import ru.otus.hw12.dto.Phone;
import ru.otus.hw12.dto.User;
import ru.otus.hw12.jetty.JettyServer;

import java.util.ArrayList;
import java.util.List;

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
        Address address = new Address();
        address.setStreet("STREET");


        User person = new User();
        person.setName("Ivan");
        person.setAge(10);
        person.setAddress(address);

        List<Phone> phones = new ArrayList<>();
        Phone phone1 = new Phone();
        phone1.setNumber("123");
        phone1.setUser(person);
        Phone phone2 = new Phone();
        phone2.setNumber("456");
        phone2.setUser(person);
        Phone phone3 = new Phone();
        phone3.setNumber("789");
        phone3.setUser(person);
        phones.add(phone1);
        phones.add(phone2);
        phones.add(phone3);
        person.setPhones(phones);

        try(DBService<User> dbHibernateServiceUser = new DBHibernateServiceUserImpl(standardServiceRegistry)) {
            dbHibernateServiceUser.create(person);

            User item = dbHibernateServiceUser.getItem(1, User.class);
            System.out.println(item);
            item.setName("ChangedUser");
            item.getPhones().get(0).setNumber("99999999999999");
            dbHibernateServiceUser.update(item);
//            System.out.println(dbHibernateServiceUser.getItem(1, User.class));

//            Thread.sleep(20000);
//            System.out.println(dbHibernateServiceUser.getItem(1, User.class));
        }

        JettyServer jettyServer = new JettyServer(standardServiceRegistry);
        jettyServer.start();
    }
}