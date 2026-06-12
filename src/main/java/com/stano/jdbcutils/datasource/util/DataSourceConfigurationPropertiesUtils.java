package com.stano.jdbcutils.datasource.util;

import com.stano.jdbcutils.datasource.DataSourceConfigurationProperties;
import com.stano.jdbcutils.datasource.DriverType;
import com.stano.jdbcutils.datasource.impl.TracingBasicDataSource;
import java.lang.reflect.Method;
import java.util.Optional;
import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;
import org.joor.Reflect;

public class DataSourceConfigurationPropertiesUtils {
    public static Optional<DataSourceConfigurationProperties> getDataSourceConfigurationProperties(
            DataSource dataSource) {
        if (dataSource instanceof TracingBasicDataSource) {
            TracingBasicDataSource tracingBasicDataSource = (TracingBasicDataSource) dataSource;

            return createDataSourceConfigurationProperties(
                    tracingBasicDataSource.getUrl(),
                    tracingBasicDataSource.getUserName(),
                    tracingBasicDataSource.getPassword());
        }

        if (dataSource instanceof BasicDataSource) {
            BasicDataSource basicDataSource = (BasicDataSource) dataSource;

            return createDataSourceConfigurationProperties(
                    basicDataSource.getUrl(),
                    basicDataSource.getUserName(),
                    basicDataSource.getPassword());
        }

        DataSource targetDataSource = getTargetDataSource(dataSource);

        if (targetDataSource != null) {
            return getDataSourceConfigurationProperties(targetDataSource);
        }

        return Optional.empty();
    }

    private static DataSource getTargetDataSource(DataSource dataSource) {
        DataSource targetDataSource = getTargetDataSourceFromSwitchableDataSource(dataSource);

        if (targetDataSource == null) {
            targetDataSource = getTargetDataSourceFromDelegatingDataSource(dataSource);
        }

        if (targetDataSource == null) {
            targetDataSource = getTargetDataSourceFromDataSourceField(dataSource);
        }

        return targetDataSource;
    }

    private static DataSource getTargetDataSourceFromSwitchableDataSource(DataSource dataSource) {
        try {
            Method method = dataSource.getClass().getDeclaredMethod("determineTargetDataSource");
            method.setAccessible(true);
            return (DataSource) method.invoke(dataSource);
        } catch (Exception ignored) {
            return null;
        }
    }

    private static DataSource getTargetDataSourceFromDelegatingDataSource(DataSource dataSource) {
        try {
            Method method = dataSource.getClass().getMethod("getTargetDataSource");
            return (DataSource) method.invoke(dataSource);
        } catch (Exception ignored) {
            return null;
        }
    }

    private static DataSource getTargetDataSourceFromDataSourceField(DataSource dataSource) {
        try {
            return Reflect.on(dataSource).get("dataSource");
        } catch (Exception ignored) {
            return null;
        }
    }

    private static Optional<DataSourceConfigurationProperties>
            createDataSourceConfigurationProperties(String url, String username, String password) {
        if (url != null) {
            DataSourceConfigurationProperties dataSourceConfigurationProperties =
                    new DataSourceConfigurationProperties();
            dataSourceConfigurationProperties.setUrl(url);
            dataSourceConfigurationProperties.setUsername(username);
            dataSourceConfigurationProperties.setPassword(password);
            dataSourceConfigurationProperties.setDriver(DriverType.fromConnectionUrl(url).name());

            return Optional.of(dataSourceConfigurationProperties);
        }

        return Optional.empty();
    }

    private DataSourceConfigurationPropertiesUtils() {}
}
