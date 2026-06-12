package com.stano.jdbcutils.datasource.impl;

import com.stano.jdbcutils.datasource.DriverType;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import javax.sql.DataSource;

public abstract class EmbeddedDataSource implements DataSource {
    private transient PrintWriter logWriter;

    private final Driver driver;
    private final String url;

    public EmbeddedDataSource(DriverType driverType, String databaseName) {
        this.driver = driverType.getJdbcDriver().getDriver();
        this.url = driverType.getJdbcDriver().getFullUrl(databaseName);
    }

    @Override
    public Connection getConnection() throws SQLException {
        return driver.connect(url, new Properties());
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        Properties info = new Properties();

        if (username != null) {
            info.setProperty("user", username);
        }

        if (password != null) {
            info.put("password", password.toCharArray());
        }

        return driver.connect(url, info);
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        throw new SQLFeatureNotSupportedException("unwrap no supported");
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        throw new SQLFeatureNotSupportedException("isWrapperFor no supported");
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return logWriter;
    }

    @Override
    public void setLogWriter(PrintWriter logWriter) throws SQLException {
        this.logWriter = logWriter;
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {}

    @Override
    public int getLoginTimeout() throws SQLException {
        return 0;
    }

    @Override
    public java.util.logging.Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }

    public abstract void shutdown();
}
