package ru.otus.hw9;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.function.Function;

public interface DBExecutor {
    int insert(String sql, QueryParam... params) throws SQLException;
    <T> T select(String sql, Function<ResultSet, T> mapper, QueryParam... params) throws SQLException;
    void execute(String sql) throws SQLException;
}
