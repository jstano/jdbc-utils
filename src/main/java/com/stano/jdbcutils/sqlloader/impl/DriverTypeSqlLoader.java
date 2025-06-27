package com.stano.jdbcutils.sqlloader.impl;

import com.stano.jdbcutils.datasource.DriverType;
import com.stano.jdbcutils.sqlloader.SqlLoader;

public class DriverTypeSqlLoader implements SqlLoader {
  private final ClassLoader classLoader;
  private final DriverType driverType;

  public DriverTypeSqlLoader(Class resourceLocator, DriverType driverType) {
    this.classLoader = resourceLocator.getClassLoader();
    this.driverType = driverType;
  }

  public String loadSql(String name) {
    return SqlLoaderCache.loadSql(name, driverType, classLoader);
  }
}
