package ru.otus.hw9.v2.dbService;

import ru.otus.hw9.Id;
import ru.otus.hw9.SQLForCreateTableEnum;
import ru.otus.hw9.tools.ReflectionHelper;
import ru.otus.hw9.v2.dto.Account;
import ru.otus.hw9.v2.jdbcTemplate.JdbcTemplate;

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

//        try {
//            return dbExecutor.select(
//                    "SELECT *\n" +
//                            "FROM account\n" +
//                            "WHERE no = ?",
//                    (resultSet) -> {
//                        try {
//                            if(resultSet.next()) {
//                                Account accountDto = new Account();
//                                accountDto.setNo(resultSet.getLong("no"));
//                                accountDto.setType(resultSet.getString("type"));
//                                accountDto.setRest(resultSet.getInt("rest"));
//                                return accountDto;
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
        jdbcTemplate.createTable(SQLForCreateTableEnum.ACCOUNT);
    }
}
