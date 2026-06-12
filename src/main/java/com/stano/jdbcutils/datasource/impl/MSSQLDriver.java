package com.stano.jdbcutils.datasource.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public final class MSSQLDriver extends AbstractDriver {
    private static final String DRIVER_PREFIX = "jdbc:sqlserver:";
    private static final String DRIVER_CLASS_NAME = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

    @Override
    public String getJdbcUrlPrefix() {
        return DRIVER_PREFIX;
    }

    @Override
    public String getDriverClass() {
        return DRIVER_CLASS_NAME;
    }

    @Override
    public String getFullUrl(String url) {
        if (url.startsWith(DRIVER_PREFIX)) {
            return url;
        }

        DataSourceUrlProperties props = DataSourceUrlParser.parseDataSourceUrl(url);

        return DRIVER_PREFIX
                + "//"
                + props.getServer()
                + ";databaseName="
                + props.getDatabase()
                + props.getOptions(); // NON-NLS
    }

    @Override
    public String getSpidQuery() {
        return "select @@SPID";
    }

    @Override
    public String getMasterDatabaseName() {
        return "master";
    }

    @Override
    protected boolean databaseExists(Statement statement, String databaseName) throws SQLException {
        try (ResultSet rs =
                statement.executeQuery(
                        String.format(
                                "select 1 from dbo.sysdatabases where name = '%s'",
                                databaseName))) {
            return rs.next();
        }
    }
}
