package com.stano.jdbcutils.utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class StatementWrapper {

    private final Connection connection;

    public StatementWrapper(Connection connection) {

        this.connection = connection;
    }

    public <T> T executeWithStatement(ExecuteWithStatement<T> executeWithStatement)
            throws SQLException {

        try (Statement statement = connection.createStatement()) {

            return executeWithStatement.executeWithStatement(statement);
        }
    }
}
