package com.stano.jdbcutils.sqlloader;

import com.stano.jdbcutils.datasource.DriverType;
import com.stano.jdbcutils.sqlloader.impl.ConnectionSqlLoader;
import com.stano.jdbcutils.sqlloader.impl.DataSourceSqlLoader;
import com.stano.jdbcutils.sqlloader.impl.DriverTypeSqlLoader;
import java.sql.Connection;
import javax.sql.DataSource;

public interface SqlLoader {
    String loadSql(String name);

    static SqlLoader with(Class resourceLocator, Connection connection) {
        return new ConnectionSqlLoader(resourceLocator, connection);
    }

    static SqlLoader with(Class resourceLocator, DataSource dataSource) {
        return new DataSourceSqlLoader(resourceLocator, dataSource);
    }

    static SqlLoader with(Class resourceLocator, DriverType driverType) {
        return new DriverTypeSqlLoader(resourceLocator, driverType);
    }
}
