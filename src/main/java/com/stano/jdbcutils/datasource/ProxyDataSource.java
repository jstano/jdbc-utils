package com.stano.jdbcutils.datasource;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ConnectionBuilder;
import java.sql.SQLException;
import java.sql.ShardingKeyBuilder;
import java.util.logging.Logger;
import javax.sql.DataSource;

public class ProxyDataSource implements DataSource {
    private final DataSource targetDataSource;

    public ProxyDataSource(DataSource targetDataSource) {
        this.targetDataSource = targetDataSource;
    }

    public DataSource getTargetDataSource() {
        return this.targetDataSource;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return targetDataSource.getConnection();
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return targetDataSource.getConnection(username, password);
    }

    @Override
    public ConnectionBuilder createConnectionBuilder() throws SQLException {
        return targetDataSource.createConnectionBuilder();
    }

    @Override
    public ShardingKeyBuilder createShardingKeyBuilder() throws SQLException {
        return targetDataSource.createShardingKeyBuilder();
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return targetDataSource.getLoginTimeout();
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        targetDataSource.setLoginTimeout(seconds);
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return targetDataSource.getLogWriter();
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        targetDataSource.setLogWriter(out);
    }

    @Override
    public Logger getParentLogger() {
        return Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T unwrap(Class<T> iface) throws SQLException {
        if (iface.isInstance(this)) {
            return (T) this;
        }
        return targetDataSource.unwrap(iface);
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return (iface.isInstance(this) || targetDataSource.isWrapperFor(iface));
    }
}
