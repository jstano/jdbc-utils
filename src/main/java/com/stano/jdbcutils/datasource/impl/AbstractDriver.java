package com.stano.jdbcutils.datasource.impl;

import com.stano.jdbcutils.datasource.JdbcDriver;
import com.stano.jdbcutils.utils.RuntimeSQLException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class AbstractDriver implements JdbcDriver {

    @Override
    public String getValidationQuery() {

        return "SELECT 1"; // NON-NLS
    }

    @Override
    public boolean supportsClob() {

        return true;
    }

    @Override
    public String getSpidForConnection(Connection connection) {

        try {
            try (Statement statement = connection.createStatement()) {
                try (ResultSet rs = statement.executeQuery(getSpidQuery())) {
                    if (rs.next()) {
                        return rs.getString(1);
                    }

                    return null;
                }
            }
        } catch (SQLException x) {
            throw new RuntimeSQLException(x);
        }
    }

    @Override
    public void createDatabase(String databaseUrl, String userName, String password)
            throws RuntimeSQLException {

        try {
            DataSourceUrlProperties props = DataSourceUrlParser.parseDataSourceUrl(databaseUrl);

            String databaseToCreate = props.getDatabase();
            String masterDatabaseUrl =
                    "//" + props.getServer() + "/" + getMasterDatabaseName() + props.getOptions();

            try (Connection connection = openConnection(masterDatabaseUrl, userName, password)) {
                try (Statement statement = connection.createStatement()) {
                    if (!databaseExists(statement, databaseToCreate)) {
                        statement.executeUpdate(
                                String.format("create database %s", databaseToCreate));
                    }
                }
            }
        } catch (SQLException x) {
            throw new RuntimeSQLException(x);
        }
    }

    protected abstract boolean databaseExists(Statement statement, String databaseName)
            throws SQLException;
}
