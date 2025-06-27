package com.stano.jdbcutils.datasource;

import java.sql.Connection;

public final class SimpleConnectionFactory {
  public static Connection createConnection(String databaseUrl) {
    JdbcDriver jdbcDriver = DriverType.fromConnectionUrl(databaseUrl).getJdbcDriver();

    return jdbcDriver.openConnection(databaseUrl, null, null);
  }

  public static Connection createConnection(String databaseUrl, String userName, String password) {
    JdbcDriver jdbcDriver = DriverType.fromConnectionUrl(databaseUrl).getJdbcDriver();

    return jdbcDriver.openConnection(databaseUrl, userName, password);
  }

  public static Connection createConnection(String databaseUrl, String userName, String password, DriverType driverType) {
    JdbcDriver jdbcDriver = driverType.getJdbcDriver();

    return jdbcDriver.openConnection(databaseUrl, userName, password);
  }

  private SimpleConnectionFactory() {
  }
}
