package com.stano.jdbcutils.datasource.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public final class MySQLDriver extends AbstractDriver {
  private static final String DRIVER_PREFIX = "jdbc:mysql:";
  private static final String DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";

  @Override
  public String getJdbcUrlPrefix() {
    return DRIVER_PREFIX;
  }

  @Override
  public String getDriverClass() {
    return DRIVER_CLASS_NAME;
  }

  @Override
  public String getSpidQuery() {
    return "select connection_id()";
  }

  @Override
  public boolean supportsClob() {
    return false;
  }

  @Override
  public String getMasterDatabaseName() {
    return "mysql";
  }

  @Override
  protected boolean databaseExists(Statement statement, String databaseName) throws SQLException {
    try (ResultSet rs = statement.executeQuery(String.format("select 1 from information_schema.schemata where schema_name = '%s'", databaseName))) {
      return rs.next();
    }
  }
}
