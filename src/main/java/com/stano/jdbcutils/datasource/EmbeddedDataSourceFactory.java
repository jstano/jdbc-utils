package com.stano.jdbcutils.datasource;

import com.stano.jdbcutils.datasource.impl.EmbeddedDataSource;
import com.stano.jdbcutils.datasource.impl.H2EmbeddedDataSource;
import com.stano.jdbcutils.datasource.impl.HSQLEmbeddedDataSource;

public final class EmbeddedDataSourceFactory {
  public static EmbeddedDataSource getEmbeddedDataSource(DriverType driverType, String databaseName) {
    if (driverType == DriverType.H2) {
      return new H2EmbeddedDataSource(databaseName);
    }

    if (driverType == DriverType.HSQL) {
      return new HSQLEmbeddedDataSource(databaseName);
    }

    throw new IllegalStateException(String.format("Unsupported driverType: '%s'", driverType.name()));
  }

  private EmbeddedDataSourceFactory() {
  }
}
