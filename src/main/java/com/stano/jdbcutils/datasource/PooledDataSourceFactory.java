package com.stano.jdbcutils.datasource;

import javax.sql.DataSource;

public interface PooledDataSourceFactory {
  DataSource createPooledDataSource(DataSourceProperties dataSourceProperties);
}
