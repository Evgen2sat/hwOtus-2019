package ru.otus.hw9.dbService;

import org.h2.engine.User;
import ru.otus.hw9.Id;
import ru.otus.hw9.QueryParam;
import ru.otus.hw9.executor.DBExecutor;
import ru.otus.hw9.executor.DBExecutorImpl;
import ru.otus.hw9.jdbcTemplate.JdbcTemplateImpl;
import ru.otus.hw9.tools.ReflectionHelper;
import ru.otus.hw9.dto.UserDto;

import java.sql.Connection;
import java.sql.SQLException;

public class DBServiceUserImpl implements DBService<UserDto> {

    private final DBExecutor dbExecutor;

    public DBServiceUserImpl() {
        Connection connection = null;
        try {
            connection = JdbcTemplateImpl.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        dbExecutor = new DBExecutorImpl(connection);
    }

    @Override
    public void create(UserDto data) {
        if(!ReflectionHelper.checkAnnotation(data)) {
            throw new IllegalArgumentException("Отсутствует поле с аннотацией @Id");
        }

        try {
            dbExecutor.insert(
                    "INSERT INTO user\n" +
                            "   (name, age)\n" +
                            "VALUES\n" +
                            "   (?, ?)",
                    QueryParam.getString(data.getName()),
                    QueryParam.getInt(data.getAge()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(UserDto data) {
        if(!ReflectionHelper.checkAnnotation(data)) {
            throw new IllegalArgumentException("Отсутствует поле с аннотацией @Id");
        }

        try {
            dbExecutor.insert(
                    "UPDATE user\n" +
                            "SET\n" +
                            "   name = ?," +
                            "   age = ?\n" +
                            "WHERE id = ?",
                    QueryParam.getString(data.getName()),
                    QueryParam.getInt(data.getAge()),
                    QueryParam.getLong(data.getId())
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public UserDto getItem(long id) {
        try {
            return dbExecutor.select(
                    "SELECT *\n" +
                            "FROM user\n" +
                            "WHERE id = ?",
                    (resultSet) -> {
                        try {
                            if(resultSet.next()) {
                                UserDto user = new UserDto();
                                user.setId(resultSet.getLong("id"));
                                user.setName(resultSet.getString("name"));
                                user.setAge(resultSet.getInt("age"));
                                return user;
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
            dbExecutor.execute("CREATE TABLE user(id bigint(20) NOT NULL auto_increment, name varchar(255), age int(3))");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
