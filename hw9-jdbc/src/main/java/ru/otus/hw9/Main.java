package ru.otus.hw9;

import ru.otus.hw9.dbService.DBService;
import ru.otus.hw9.dbService.DBServiceAccountImpl;
import ru.otus.hw9.dbService.DBServiceUserImpl;
import ru.otus.hw9.dto.AccountDto;
import ru.otus.hw9.dto.UserDto;
import ru.otus.hw9.jdbcTemplate.JdbcTemplate;
import ru.otus.hw9.jdbcTemplate.JdbcTemplateImpl;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try {
            DBService<UserDto> userDtoDBService = new DBServiceUserImpl();
            JdbcTemplate<UserDto> userJdbcTemplate = new JdbcTemplateImpl<>(userDtoDBService);
            userJdbcTemplate.createTable();
            UserDto user = new UserDto();
            user.setName("User1");
            user.setAge(3);
            userJdbcTemplate.create(user);
            UserDto loadedUser = userJdbcTemplate.load(1);
            System.out.println(loadedUser);
            loadedUser.setName("ChangedUser1");
            userJdbcTemplate.update(loadedUser);
            System.out.println(userJdbcTemplate.load(1));

            DBService<AccountDto> accountDtoDBService = new DBServiceAccountImpl();
            JdbcTemplate<AccountDto> accountJdbcTemplate = new JdbcTemplateImpl<>(accountDtoDBService);
            accountJdbcTemplate.createTable();
            AccountDto accountDto = new AccountDto();
            accountDto.setType("Type1");
            accountDto.setRest(10);
            accountJdbcTemplate.create(accountDto);
            AccountDto loadedAccount = accountJdbcTemplate.load(1);
            System.out.println(loadedAccount);
            loadedAccount.setType("ChangedType1");
            accountJdbcTemplate.update(loadedAccount);
            System.out.println(accountJdbcTemplate.load(1));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
