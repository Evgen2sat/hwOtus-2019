package ru.otus.hw11;

import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.otus.hw11.dbService.hibernate.DBHibernateServiceUserImpl;
import ru.otus.hw11.dto.Address;
import ru.otus.hw11.dto.Phone;
import ru.otus.hw11.dto.User;

import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final StandardServiceRegistry standardServiceRegistry;

    static {
        try {
//            Configuration configuration = new Configuration();
//            configuration.configure("hibernate.cfg.xml");
//
//            standardServiceRegistry = new StandardServiceRegistryBuilder()
//                    .applySettings(configuration.getProperties())
//                    .build();

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

        DBHibernateServiceUserImpl dbHibernateServiceUser = new DBHibernateServiceUserImpl(standardServiceRegistry);
        dbHibernateServiceUser.create(person);

        User item = dbHibernateServiceUser.getItem(1, User.class);
        System.out.println(item);
        item.setName("ChangedUser");
        item.getPhones().get(0).setNumber("99999999999999");
        dbHibernateServiceUser.update(item);
        System.out.println(dbHibernateServiceUser.getItem(1, User.class));


//        Account account = new Account();
//        account.setType("Ivan");
//        account.setRest(BigDecimal.valueOf(10));
//
//        DBHibernateServiceAccountImpl dbHibernateServiceAccount = new DBHibernateServiceAccountImpl(standardServiceRegistry);
//        dbHibernateServiceAccount.create(account);
//
//        Account itemAccount = dbHibernateServiceAccount.getItem(1, Account.class);
//        System.out.println(itemAccount);
//        itemAccount.setType("ChangedAccount");
//        dbHibernateServiceAccount.update(itemAccount);
//        System.out.println(dbHibernateServiceAccount.getItem(1, Account.class));
    }
}