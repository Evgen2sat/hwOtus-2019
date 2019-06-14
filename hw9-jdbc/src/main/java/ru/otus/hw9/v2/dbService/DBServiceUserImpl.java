package ru.otus.hw9.v2.dbService;

import ru.otus.hw9.Id;
import ru.otus.hw9.SQLForCreateTableEnum;
import ru.otus.hw9.tools.ReflectionHelper;
import ru.otus.hw9.v2.dto.User;
import ru.otus.hw9.v2.jdbcTemplate.JdbcTemplate;

public class DBServiceUserImpl implements DBService<User> {

    private final JdbcTemplate<User> jdbcTemplate;

    public DBServiceUserImpl(JdbcTemplate<User> jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(User data) {
        if(!ReflectionHelper.checkAnnotation(data)) {
            throw new IllegalArgumentException("Отсутствует поле с аннотацией @Id");
        }

        jdbcTemplate.create(data);
    }

    @Override
    public void update(User data) {
        if(!ReflectionHelper.checkAnnotation(data)) {
            throw new IllegalArgumentException("Отсутствует поле с аннотацией @Id");
        }

        jdbcTemplate.update(data);
    }

    @Override
    public User getItem(long id, Class<User> clazz) {

        return jdbcTemplate.load(id, clazz);
//        try {
//            return dbExecutor.select(
//                    "SELECT *\n" +
//                            "FROM user\n" +
//                            "WHERE id = ?",
//                    (resultSet) -> {
//                        try {
//                            if(resultSet.next()) {
//                                User user = new User();
//                                user.setId(resultSet.getLong("id"));
//                                user.setName(resultSet.getString("name"));
//                                user.setAge(resultSet.getInt("age"));
//                                return user;
//                            }
//                            return null;
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                            return null;
//                        }
//                    },
//                    QueryParam.getLong(id)
//            );
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

    }

    @Override
    public void createTable() {
        jdbcTemplate.createTable(SQLForCreateTableEnum.USER);
    }
}
