package ru.otus.hw9.dbService;

import ru.otus.hw9.SQLForCreateTableEnum;
import ru.otus.hw9.tools.ReflectionHelper;
import ru.otus.hw9.dto.User;
import ru.otus.hw9.jdbcTemplate.JdbcTemplate;

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
    }

    @Override
    public void createTable() {
        jdbcTemplate.createTable(SQLForCreateTableEnum.USER);
    }
}
