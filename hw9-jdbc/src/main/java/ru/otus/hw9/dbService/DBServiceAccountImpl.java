package ru.otus.hw9.dbService;

import ru.otus.hw9.Id;
import ru.otus.hw9.QueryParam;
import ru.otus.hw9.dto.AccountDto;
import ru.otus.hw9.dto.UserDto;
import ru.otus.hw9.executor.DBExecutor;
import ru.otus.hw9.executor.DBExecutorImpl;
import ru.otus.hw9.jdbcTemplate.JdbcTemplateImpl;
import ru.otus.hw9.tools.ReflectionHelper;

import java.sql.Connection;
import java.sql.SQLException;

public class DBServiceAccountImpl implements DBService<AccountDto> {

    private final DBExecutor dbExecutor;

    public DBServiceAccountImpl() {
        Connection connection = null;
        try {
            connection = JdbcTemplateImpl.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        dbExecutor = new DBExecutorImpl(connection);
    }

    @Override
    public void create(AccountDto data) {
        if(!ReflectionHelper.checkAnnotation(data)) {
            throw new IllegalArgumentException("Отсутствует поле с аннотацией @Id");
        }

        try {
            dbExecutor.insert(
                    "INSERT INTO account\n" +
                            "   (type, rest)\n" +
                            "VALUES\n" +
                            "   (?, ?)",
                    QueryParam.getString(data.getType()),
                    QueryParam.getInt(data.getRest()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(AccountDto data) {
        if(!ReflectionHelper.checkAnnotation(data)) {
            throw new IllegalArgumentException("Отсутствует поле с аннотацией @Id");
        }

        try {
            dbExecutor.insert(
                    "UPDATE account\n" +
                            "SET\n" +
                            "   type = ?," +
                            "   rest = ?\n" +
                            "WHERE no = ?",
                    QueryParam.getString(data.getType()),
                    QueryParam.getInt(data.getRest()),
                    QueryParam.getLong(data.getNo())
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public AccountDto getItem(long id) {
        try {
            return dbExecutor.select(
                    "SELECT *\n" +
                            "FROM account\n" +
                            "WHERE no = ?",
                    (resultSet) -> {
                        try {
                            if(resultSet.next()) {
                                AccountDto accountDto = new AccountDto();
                                accountDto.setNo(resultSet.getLong("no"));
                                accountDto.setType(resultSet.getString("type"));
                                accountDto.setRest(resultSet.getInt("rest"));
                                return accountDto;
                            }
                            return null;
                        } catch (Exception e) {
                            e.printStackTrace();
                            return null;
                        }
                    },
                    QueryParam.getLong(id)
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void createTable() {
        try {
            dbExecutor.execute("CREATE TABLE account(no bigint(20) NOT NULL auto_increment, type varchar(255), rest number)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
