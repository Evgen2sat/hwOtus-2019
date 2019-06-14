package ru.otus.hw9.v2.executor;

import ru.otus.hw9.QueryParam;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Function;

public interface DBExecutor {
    long insert(String sql, Object... params) throws SQLException;
    void update(String sql, Object... params) throws SQLException;
    <T> T select(String sql, Function<ResultSet, T> mapper, Object... params) throws SQLException;
    void execute(String sql) throws SQLException;
}
