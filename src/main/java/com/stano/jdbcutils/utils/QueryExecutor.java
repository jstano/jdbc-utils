package com.stano.jdbcutils.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class QueryExecutor {
    private final Connection connection;
    private final String sqlQuery;

    public QueryExecutor(Connection connection, String sqlQuery) {
        this.connection = connection;
        this.sqlQuery = sqlQuery;
    }

    public void execute(RowConsumer rowConsumer) {
        try (Statement st = connection.createStatement()) {
            try (ResultSet rs = st.executeQuery(sqlQuery)) {
                while (rs.next()) {
                    rowConsumer.consume(rs);
                }
            }
        } catch (SQLException x) {
            throw new RuntimeSQLException(x);
        }
    }
}
