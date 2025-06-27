package com.stano.jdbcutils.datasource.impl;

import com.stano.jdbcutils.utils.RuntimeSQLException;

import java.sql.SQLException;
import java.sql.Statement;

public final class H2Driver extends AbstractDriver {
  private static final String DRIVER_PREFIX = "jdbc:h2:";
  private static final String DRIVER_CLASS_NAME = "org.h2.Driver";

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
    return "select session_id()";
  }

  @Override
  public String getMasterDatabaseName() {
    return null;
  }

  @Override
  public void createDatabase(String databaseUrl, String userName, String password) throws RuntimeSQLException {
  }

  @Override
  protected boolean databaseExists(Statement statement, String databaseName) throws SQLException {
    return true;
  }
}
