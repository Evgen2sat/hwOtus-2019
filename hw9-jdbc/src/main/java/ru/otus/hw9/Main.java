package ru.otus.hw9;

import ru.otus.hw9.dto.Account;
import ru.otus.hw9.dto.User;
import ru.otus.hw9.dbService.DBService;
import ru.otus.hw9.dbService.DBServiceAccountImpl;
import ru.otus.hw9.dbService.DBServiceUserImpl;
import ru.otus.hw9.jdbcTemplate.JdbcTemplateImpl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {

    private static final String URL = "jdbc:h2:mem:";

    public static void main(String[] args) throws Exception {
        Connection connection = getConnection();

        DBService<User> userDBService = new DBServiceUserImpl(new JdbcTemplateImpl<>(connection));
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


        DBService<Account> accountDBService = new DBServiceAccountImpl(new JdbcTemplateImpl<>(connection));
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

        connection.close();
    }

    public static Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(URL);
        connection.setAutoCommit(false);
        return connection;
    }
}
