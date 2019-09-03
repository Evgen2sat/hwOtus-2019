package su.medsoft.mis.er.terminal.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import su.medsoft.mis.er.terminal.Application;
import su.medsoft.mis.er.terminal.database.QueryParam;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Singleton
public class DatabaseService {
    private static final Logger LOG = LoggerFactory.getLogger(Application.getLoggerName());

    @Inject
    private DataSource dataSource;

    public DatabaseService() {}

    public <T> List<T> executeSelectQuery(
            String sql, Function<ResultSet, T> mapper, QueryParam...params) {

        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {

            for(int i = 0; i < params.length; i++) {
                appendQueryParam(i + 1, params[i], statement);
            }

            ResultSet rs = statement.executeQuery();

            List<T> result = new ArrayList<>();

            while(rs.next()) {

                result.add(mapper.apply(rs));

            }

            return result;
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public <T> T executeSelectQueryWithAllItems(String sql, Function<ResultSet, T> mapper, QueryParam...params) {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {

            for(int i = 0; i < params.length; i++) {
                appendQueryParam(i + 1, params[i], statement);
            }

            ResultSet rs = statement.executeQuery();

            return mapper.apply(rs);
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public <T> T executeSelectQueryWithAllItemsWithoutParams(String sql, Function<ResultSet, T> mapper) {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {

            ResultSet rs = statement.executeQuery();

            return mapper.apply(rs);
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private void appendQueryParam(int index, QueryParam param, PreparedStatement statement) throws SQLException {

        if (param.getValue() == null) {
            statement.setNull(index, param.getType());
        } else {
            statement.setObject(index, param.getValue(), param.getType());
        }

    }
}
