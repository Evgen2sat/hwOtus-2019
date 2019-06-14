package ru.otus.hw9.v2;

import ru.otus.hw9.v2.dto.Account;
import ru.otus.hw9.v2.dto.User;
import ru.otus.hw9.v2.dbService.DBService;
import ru.otus.hw9.v2.dbService.DBServiceAccountImpl;
import ru.otus.hw9.v2.dbService.DBServiceUserImpl;
import ru.otus.hw9.v2.jdbcTemplate.JdbcTemplateImpl;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) throws Exception {
        DBService<User> userDBService = new DBServiceUserImpl(new JdbcTemplateImpl<>());
        userDBService.createTable();
        User user = new User();
        user.setName("User1");
        user.setAge(3);
        System.out.println("Оригинал " + user);
        userDBService.create(user);
        System.out.println("После создания в БД " + user);
        user.setName("ChangedUser1");
        userDBService.update(user);
        System.out.println("После изменения в БД " + user);
        System.out.println(userDBService.getItem(user.getId(), User.class));


        DBService<Account> accountDBService = new DBServiceAccountImpl(new JdbcTemplateImpl<>());
        accountDBService.createTable();
        Account account = new Account();
        account.setType("Type1");
        account.setRest(BigDecimal.valueOf(10));
        System.out.println("Оригинал " + account);
        accountDBService.create(account);
        System.out.println("После создания в БД " + account);
        account.setType("ChangedType1");
        accountDBService.update(account);
        System.out.println("После изменения в БД " + account);
        System.out.println(accountDBService.getItem(2, Account.class));
    }
}
