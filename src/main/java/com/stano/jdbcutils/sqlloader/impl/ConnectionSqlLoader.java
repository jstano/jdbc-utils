package com.stano.jdbcutils.sqlloader.impl;

import com.stano.jdbcutils.datasource.DriverType;

import java.sql.Connection;

public class ConnectionSqlLoader extends DriverTypeSqlLoader {
  public ConnectionSqlLoader(Class resourceLocator, Connection connection) {
    super(resourceLocator, DriverType.fromConnection(connection));
  }
}
