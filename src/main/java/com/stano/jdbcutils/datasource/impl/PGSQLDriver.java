package com.stano.jdbcutils.datasource.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public final class PGSQLDriver extends AbstractDriver {
    private static final String DRIVER_PREFIX = "jdbc:postgresql:";
    private static final String DRIVER_CLASS_NAME = "org.postgresql.Driver";

    @Override
    public String getJdbcUrlPrefix() {
        return DRIVER_PREFIX;
    }

    @Override
    public String getDriverClass() {
        return DRIVER_CLASS_NAME;
    }

    @Override
    public String getSpidQuery() {
        return "select pg_backend_pid()";
    }

    @Override
    public boolean supportsClob() {
        return false;
    }

    @Override
    public String getMasterDatabaseName() {
        return "postgres";
    }

    @Override
    protected boolean databaseExists(Statement statement, String databaseName) throws SQLException {
        try (ResultSet rs =
                statement.executeQuery(
                        String.format(
                                "select 1 from pg_database where datname = '%s'", databaseName))) {
            return rs.next();
        }
    }
}
