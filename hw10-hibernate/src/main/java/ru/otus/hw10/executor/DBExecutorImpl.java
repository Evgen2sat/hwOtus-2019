package ru.otus.hw10.executor;

import java.sql.*;
import java.util.function.Function;

public class DBExecutorImpl implements DBExecutor {

    private final Connection connection;

    public DBExecutorImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public long insert(String sql, Object... params) throws SQLException {
        Savepoint savepoint = connection.setSavepoint("savePoint");

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            setParams(preparedStatement, params);

            preparedStatement.executeUpdate();

            long id = -1L;

            try(ResultSet rs = preparedStatement.getGeneratedKeys()) {
                if(rs.next()) {
                    id = rs.getLong(1);
                }
            }

            this.connection.commit();

            return id;
        } catch (Exception e) {
            this.connection.rollback(savepoint);
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Override
    public <T> T select(String sql, Function<ResultSet, T> mapper, Object... params) throws SQLException {
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            setParams(preparedStatement, params);

            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                return mapper.apply(resultSet);
            }
        }
    }

    private void setParams(PreparedStatement preparedStatement, Object...params) throws SQLException {
        for(int i = 0; i < params.length; i++) {
            if(params[i] == null) {
                preparedStatement.setNull(i+1, Types.NULL);
            } else {
                preparedStatement.setObject(i+1, params[i]);
            }
        }
    }

    @Override
    public void execute(String sql) throws SQLException {
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void update(String sql, Object... params) throws SQLException {
        insert(sql, params);
    }
}
