package com.stano.jdbcutils.datasource.impl;

public class DataSourceUrlProperties {
    private final String server;
    private final String database;
    private final String options;

    public DataSourceUrlProperties(String server, String database, String options) {
        this.server = server;
        this.database = database;
        this.options = options;
    }

    public String getServer() {
        return server;
    }

    public String getDatabase() {
        return database;
    }

    public String getOptions() {
        return options;
    }
}
