package ru.otus.hw9;

import java.sql.*;
import java.util.Optional;
import java.util.function.Function;

public class DBExecutorImpl implements DBExecutor {

    private final Connection connection;

    public DBExecutorImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public int insert(String sql, QueryParam... params) throws SQLException {
        Savepoint savepoint = connection.setSavepoint("savePoint");

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            setParams(preparedStatement, params);

            return preparedStatement.executeUpdate();

        } catch (Exception e) {
            this.connection.rollback(savepoint);
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Override
    public <T> T select(String sql, Function<ResultSet, T> mapper, QueryParam... params) throws SQLException {
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            setParams(preparedStatement, params);

            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                return mapper.apply(resultSet);
            }
        }
    }

    private void setParams(PreparedStatement preparedStatement, QueryParam...params) throws SQLException {
        for(int i = 0; i < params.length; i++) {
            if(params[i].getValue() == null) {
                preparedStatement.setNull(i+1, params[i].getType());
            } else {
                preparedStatement.setObject(i+1, params[i].getValue(), params[i].getType());
            }
        }
    }

    @Override
    public void execute(String sql) throws SQLException {
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
        }
    }
}
