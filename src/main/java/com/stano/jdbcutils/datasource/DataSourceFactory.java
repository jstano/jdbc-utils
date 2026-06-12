package com.stano.jdbcutils.datasource;

import com.stano.jdbcutils.datasource.impl.DbcpDataSourceFactory;
import java.sql.Connection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.sql.DataSource;

public final class DataSourceFactory {
    private static final Map<DataSourceProperties, DataSource> dataSourceMap =
            new ConcurrentHashMap<>();

    public static DataSource getDataSource(DataSourceProperties dataSourceProperties) {
        DataSource dataSource = dataSourceMap.get(dataSourceProperties);

        if (dataSource == null) {
            dataSource = createDataSource(dataSourceProperties);

            dataSourceMap.put(dataSourceProperties, dataSource);
        }

        return dataSource;
    }

    public static DataSource getDataSource(
            String databaseUrl, String userName, String password, DriverType driverType) {
        return getDataSource(new DataSourceProperties(databaseUrl, userName, password, driverType));
    }

    public static DataSource getDataSource(String databaseUrl, String userName, String password) {
        return getDataSource(new DataSourceProperties(databaseUrl, userName, password));
    }

    public static DataSource getDataSource(Connection connection) {
        return new ConnectionDataSource(new DelegatingConnection(connection));
    }

    public static DataSource getDataSourceOwnsConnection(Connection connection) {
        return new ConnectionDataSource(new DelegatingConnection(connection, true));
    }

    private static DataSource createDataSource(DataSourceProperties dataSourceProperties) {
        return getPooledDataSourceFactory().createPooledDataSource(dataSourceProperties);
    }

    private static PooledDataSourceFactory getPooledDataSourceFactory() {
        return new DbcpDataSourceFactory();
    }

    private DataSourceFactory() {}
}
