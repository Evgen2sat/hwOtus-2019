package ru.otus.hw9;

import java.lang.annotation.Annotation;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserJdbcTemplateImpl implements JdbcTemplate<User> {

    private static final String URL = "jdbc:h2:mem:";
    private final DBExecutor dbExecutor;

    public UserJdbcTemplateImpl() throws SQLException {
        Connection connection = getConnection();
        dbExecutor = new DBExecutorImpl(connection);
    }

    @Override
    public void create(User data) {
        if(!checkAnnotation(data, Id.class)) {
            throw new IllegalArgumentException("Отсутствует поле с аннотацией @Id");
        }

        try {
            System.out.println(dbExecutor.insert(
                    "INSERT INTO user\n" +
                    "   (name, age)\n" +
                    "VALUES\n" +
                    "   (?, ?)",
                    QueryParam.getString(data.getName()),
                    QueryParam.getInt(data.getAge())));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(User data) {
        if(!checkAnnotation(data, Id.class)) {
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
    public void createOrUpdate(User data) {
        if(!checkAnnotation(data, Id.class)) {
            throw new IllegalArgumentException("Отсутствует поле с аннотацией @Id");
        }

        if(data.getId() == null) {
            create(data);
        } else {
            update(data);
        }
    }

    @Override
    public User load(long id) {
        try {
            return dbExecutor.select(
                    "SELECT *\n" +
                            "FROM user\n" +
                            "WHERE id = ?",
                    (resultSet) -> {
                        try {
                            if(resultSet.next()) {
                                User user = new User();
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

    private Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(URL);
        connection.setAutoCommit(false);
        return connection;
    }

    @Override
    public boolean checkAnnotation(Object object, Class<? extends Annotation> clazz) {
        return ReflectionHelper.checkAnnotation(object, clazz);
    }
}
