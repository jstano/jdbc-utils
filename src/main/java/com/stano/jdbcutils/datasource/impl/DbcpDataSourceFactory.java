package com.stano.jdbcutils.datasource.impl;

import com.stano.jdbcutils.datasource.DataSourceProperties;
import com.stano.jdbcutils.datasource.DriverType;
import com.stano.jdbcutils.datasource.JdbcDriver;
import com.stano.jdbcutils.datasource.PooledDataSourceFactory;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;

public class DbcpDataSourceFactory implements PooledDataSourceFactory {
    private static final long TIME_BETWEEN_EVICTION_RUNS_MILLIS = Duration.ofMinutes(30).toMillis();
    private static final long MIN_EVICTABLE_IDLE_TIMEOUT_MILLIS = Duration.ofMinutes(5).toMillis();
    private static final int REMOVE_ABANDONED_TIMEOUT_SECONDS =
            (int) Duration.ofMinutes(30).getSeconds();

    public DataSource createPooledDataSource(DataSourceProperties dataSourceProperties) {
        JdbcDriver jdbcDriver = dataSourceProperties.getDriverType().getJdbcDriver();

        BasicDataSource dataSource = new BasicDataSource();

        DataSourceUrlProperties dataSourceUrlProperties =
                DataSourceUrlParser.parseDataSourceUrl(dataSourceProperties.getUrl());

        dataSource.setJmxName(
                String.format(
                        "org.apache.commons.dbcp2:name=data_source_%s_%s,type=BasicDataSource",
                        dataSourceUrlProperties.getServer(),
                        dataSourceUrlProperties.getDatabase()));

        dataSource.setDriverClassName(jdbcDriver.getDriverClass());
        dataSource.setUrl(jdbcDriver.getFullUrl(dataSourceProperties.getUrl()));
        dataSource.setUsername(dataSourceProperties.getUsername());
        dataSource.setPassword(dataSourceProperties.getPassword());

        dataSource.setInitialSize(0);
        dataSource.setMaxTotal(100);
        dataSource.setMinIdle(0);
        dataSource.setMaxIdle(8);

        dataSource.setTestOnBorrow(true);
        dataSource.setTestOnReturn(true);
        dataSource.setTestWhileIdle(true);

        dataSource.setTimeBetweenEvictionRunsMillis(TIME_BETWEEN_EVICTION_RUNS_MILLIS);
        dataSource.setMinEvictableIdleTimeMillis(MIN_EVICTABLE_IDLE_TIMEOUT_MILLIS);
        dataSource.setNumTestsPerEvictionRun(10);

        dataSource.setRemoveAbandonedOnMaintenance(true);
        dataSource.setRemoveAbandonedTimeout(REMOVE_ABANDONED_TIMEOUT_SECONDS);

        if (System.getProperty("DEBUG_CONNECTION_LEAKS") != null) {
            dataSource.setLogAbandoned(true);
            dataSource.setAbandonedUsageTracking(true);
        }

        dataSource.setValidationQuery(jdbcDriver.getValidationQuery());
        dataSource.setConnectionProperties(getConnectionProperties(dataSourceProperties));

        if (dataSourceProperties.getDriverType() == DriverType.PGSQL) {
            fixPostgresCaseSensitivityTypeCastingIssue(dataSource);
        }

        if (dataSourceProperties.getDriverType() == DriverType.MSSQL) {
            if (Boolean.parseBoolean(System.getProperty("r365.sqlserver.unicode", "false"))) {
                dataSource.addConnectionProperty("sendStringParametersAsUnicode", "false");
            }
        }

        //    TelemetryFactory.initialize();

        return new TracingBasicDataSource(dataSource);
    }

    private void fixPostgresCaseSensitivityTypeCastingIssue(BasicDataSource dataSource) {
        dataSource.addConnectionProperty("stringtype", "unspecified");
    }

    private String getConnectionProperties(DataSourceProperties dataSourceProperties) {
        boolean jdbcEncryptionEnabled =
                Boolean.parseBoolean(System.getProperty("JDBC_ENCRYPTION_ENABLED"));
        boolean jdbcEncryptionTrustCerts =
                Boolean.parseBoolean(System.getProperty("JDBC_ENCRYPTION_TRUST_CERTS"));
        DriverType driverType = dataSourceProperties.getDriverType();
        List<String> connectionProperties = new ArrayList<>();

        if (jdbcEncryptionEnabled) {
            if (driverType == DriverType.MSSQL) {
                connectionProperties.add("encrypt=true");
            } else if (driverType == DriverType.PGSQL) {
                connectionProperties.add("ssl=true");
            }
        }

        if (jdbcEncryptionTrustCerts) {
            if (driverType == DriverType.MSSQL) {
                connectionProperties.add("trustServerCertificate=true");
            } else if (driverType == DriverType.PGSQL) {
                connectionProperties.add("sslmode=allow");
            }
        }

        return String.join(";", connectionProperties);
    }
}
