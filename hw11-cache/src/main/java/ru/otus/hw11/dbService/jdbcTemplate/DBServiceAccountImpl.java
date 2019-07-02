package ru.otus.hw11.dbService.jdbcTemplate;

import ru.otus.hw11.SQLForCreateTableEnum;
import ru.otus.hw11.dbService.DBService;
import ru.otus.hw11.dto.Account;
import ru.otus.hw11.jdbcTemplate.JdbcTemplate;
import ru.otus.hw11.tools.ReflectionHelper;

public class DBServiceAccountImpl implements DBService<Account> {

    private final JdbcTemplate<Account> jdbcTemplate;

    public DBServiceAccountImpl(JdbcTemplate<Account> jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(Account data) {
        if(!ReflectionHelper.checkAnnotation(data)) {
            throw new IllegalArgumentException("Отсутствует поле с аннотацией @Id");
        }

        jdbcTemplate.create(data);
    }

    @Override
    public void update(Account data) {
        if(!ReflectionHelper.checkAnnotation(data)) {
            throw new IllegalArgumentException("Отсутствует поле с аннотацией @Id");
        }

        jdbcTemplate.update(data);
    }

    @Override
    public Account getItem(long id, Class<Account> clazz) {
        return jdbcTemplate.load(id, clazz);
    }

    @Override
    public void createTable() {
        jdbcTemplate.createTable(SQLForCreateTableEnum.ACCOUNT);
    }
}
