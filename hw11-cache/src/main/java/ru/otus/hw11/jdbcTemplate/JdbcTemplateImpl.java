package ru.otus.hw11.jdbcTemplate;

import ru.otus.hw11.SQLForCreateTableEnum;
import ru.otus.hw11.executor.DBExecutor;
import ru.otus.hw11.executor.DBExecutorImpl;
import ru.otus.hw11.tools.ReflectionHelper;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class JdbcTemplateImpl<T> implements JdbcTemplate<T> {

    private final DBExecutor dbExecutor;

    public JdbcTemplateImpl(Connection connection) throws SQLException {
        dbExecutor = new DBExecutorImpl(connection);
    }

    @Override
    public void create(T data) {
        try {
            List<String> columnsByFields = ReflectionHelper.getColumnsByFields(data);

            long id = dbExecutor.insert(
                    String.format("INSERT INTO %s\n" +
                            "(%s)\n" +
                            "VALUES\n" +
                            "(%s)",
                            ReflectionHelper.getClassName(data),
                            String.join(",", columnsByFields),
                            ReflectionHelper.getParams(data)),
                    (Object[]) columnsByFields.stream().map(fieldName -> {
                        return ReflectionHelper.getValueByName(fieldName, data);
                    }).toArray()
            );

            ReflectionHelper.setIdToData(id, data);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(T data) {
        try {
            List<String> columnsByFields = ReflectionHelper.getColumnsByFields(data);
            String idFieldName = ReflectionHelper.getIdFieldName(data);

            List<Object> values = columnsByFields.stream().map(fieldName -> {
                return ReflectionHelper.getValueByName(fieldName, data);
            }).collect(Collectors.toList());
            values.add(ReflectionHelper.getValueByName(idFieldName, data));

            dbExecutor.update(
                    String.format("UPDATE %s\n" +
                            "SET\n" +
                            "%s\n" +
                            "WHERE %s = ?",
                            ReflectionHelper.getClassName(data),
                            String.join(" = ?, ", columnsByFields) + " = ? ",
                            idFieldName),
                    values.toArray()
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createOrUpdate(T data) {
        if (!checkAnnotation(data)) {
            throw new IllegalArgumentException("Отсутствует поле с аннотацией @Id");
        }

        try {
            if (ReflectionHelper.checkNullIdField(data)) {
                create(data);
            } else {
                update(data);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public T load(long id, Class<T> clazz) {

        List<String> fieldsName = ReflectionHelper.getFieldsName(clazz);

        T result = null;
        try {
            result = dbExecutor.select(
                    String.format("SELECT %s\n" +
                                    "FROM %s\n" +
                                    "WHERE %s = ?",
                            String.join(",", fieldsName),
                            ReflectionHelper.getSimpleClassName(clazz),
                            ReflectionHelper.getIdFieldName(clazz)),
                    resultSet ->  ReflectionHelper.createObject(clazz, resultSet, fieldsName),
                    id
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public void createTable(SQLForCreateTableEnum type) {
        try {
            dbExecutor.execute(type.getSql());
            System.out.println("Таблица " + type + " успешно создана");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Создание таблицы " + type + " завершилось ошибкой");
        }
    }

    @Override
    public boolean checkAnnotation(Object object) {
        return ReflectionHelper.checkAnnotation(object);
    }
}
