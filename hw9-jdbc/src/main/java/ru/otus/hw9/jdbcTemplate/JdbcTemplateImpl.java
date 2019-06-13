package ru.otus.hw9.jdbcTemplate;

import ru.otus.hw9.Id;
import ru.otus.hw9.QueryParam;
import ru.otus.hw9.dbService.DBService;
import ru.otus.hw9.tools.ReflectionHelper;
import ru.otus.hw9.dto.UserDto;
import ru.otus.hw9.executor.DBExecutor;
import ru.otus.hw9.executor.DBExecutorImpl;

import java.lang.annotation.Annotation;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcTemplateImpl<T> implements JdbcTemplate<T> {

    private static final String URL = "jdbc:h2:mem:";
    private final DBExecutor dbExecutor;
    private final DBService<T> dbService;

    public JdbcTemplateImpl(DBService<T> dbService) throws SQLException {
        Connection connection = getConnection();
        dbExecutor = new DBExecutorImpl(connection);
        this.dbService = dbService;
    }

    @Override
    public void create(T data) {
        dbService.create(data);
    }

    @Override
    public void update(T data) {
        dbService.update(data);
    }

    @Override
    public void createOrUpdate(T data) {
        if(!checkAnnotation(data, Id.class)) {
            throw new IllegalArgumentException("Отсутствует поле с аннотацией @Id");
        }

        try {
            if(ReflectionHelper.checkNullIdField(data, Id.class)) {
                create(data);
            } else {
                update(data);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public T load(long id) {
        return dbService.getItem(id);
    }

    @Override
    public void createTable() {
        dbService.createTable();
    }

    public static Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(URL);
        connection.setAutoCommit(false);
        return connection;
    }

    @Override
    public boolean checkAnnotation(Object object, Class<? extends Annotation> clazz) {
        return ReflectionHelper.checkAnnotation(object, clazz);
    }
}
