package com.stano.jdbcutils.sqlloader.impl;

import com.stano.jdbcutils.datasource.DriverType;
import com.stano.jdbcutils.sqlloader.SqlLoader;

import javax.sql.DataSource;

public class DataSourceSqlLoader implements SqlLoader {
  private final ClassLoader classLoader;
  private final DataSource dataSource;

  public DataSourceSqlLoader(Class resourceLocator, DataSource dataSource) {
    this.classLoader = resourceLocator.getClassLoader();
    this.dataSource = dataSource;
  }

  public String loadSql(String name) {
    return SqlLoaderCache.loadSql(name, DriverType.fromDataSource(dataSource), classLoader);
  }
}
