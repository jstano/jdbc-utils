package com.stano.jdbcutils.datasource.util;

import com.stano.jdbcutils.datasource.DataSourceConfigurationProperties;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class DataSourceUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataSourceUtil.class);

    public static String getUrlFromDataSource(DataSource dataSource) {
        Optional<DataSourceConfigurationProperties> dataSourceConfigurationProperties =
                DataSourceConfigurationPropertiesUtils.getDataSourceConfigurationProperties(
                        dataSource);

        if (dataSourceConfigurationProperties.isPresent()) {
            return dataSourceConfigurationProperties.get().getUrl();
        }

        String url = getUrlFromGetUrlMethod(dataSource);

        if (url != null) {
            return url;
        }

        return getUrlFromConnection(dataSource);
    }

    private static String getUrlFromGetUrlMethod(DataSource dataSource) {
        try {
            Method method = dataSource.getClass().getMethod("getUrl");

            return (String) method.invoke(dataSource);
        } catch (IllegalAccessException
                | InvocationTargetException
                | NoSuchMethodException ignored) {
        }

        return null;
    }

    private static String getUrlFromConnection(DataSource dataSource) {
        try (Connection connection = dataSource.getConnection()) {
            return connection.getMetaData().getURL();
        } catch (SQLException ignored) {
            LOGGER.error("Error connecting to database", ignored);
        }

        return null;
    }

    private DataSourceUtil() {}
}
