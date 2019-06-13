package ru.otus.hw9;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try {
            UserJdbcTemplateImpl userJdbcTemplate = new UserJdbcTemplateImpl();
            userJdbcTemplate.createTable();

            User user = new User();
            user.setName("User1");
            user.setAge(3);
            userJdbcTemplate.create(user);
            User loadedUser = userJdbcTemplate.load(1);
            System.out.println(loadedUser);
            loadedUser.setName("ChangedUser1");
            userJdbcTemplate.update(loadedUser);
            System.out.println(userJdbcTemplate.load(1));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
