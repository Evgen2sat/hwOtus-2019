package ru.otus.hw11.dbService.jdbcTemplate;

import ru.otus.hw11.SQLForCreateTableEnum;
import ru.otus.hw11.dbService.DBService;
import ru.otus.hw11.dto.User;
import ru.otus.hw11.jdbcTemplate.JdbcTemplate;
import ru.otus.hw11.tools.ReflectionHelper;

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
