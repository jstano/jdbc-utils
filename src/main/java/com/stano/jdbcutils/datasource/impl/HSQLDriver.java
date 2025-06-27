package com.stano.jdbcutils.datasource.impl;

import com.stano.jdbcutils.utils.RuntimeSQLException;

import java.sql.SQLException;
import java.sql.Statement;

public final class HSQLDriver extends AbstractDriver {
  private static final String DRIVER_PREFIX = "jdbc:hsqldb:";
  private static final String DRIVER_CLASS_NAME = "org.hsqldb.jdbcDriver";

  @Override
  public String getJdbcUrlPrefix() {
    return DRIVER_PREFIX;
  }

  @Override
  public String getDriverClass() {
    return DRIVER_CLASS_NAME;
  }

  @Override
  public String getValidationQuery() {
    return "SELECT 1 FROM INFORMATION_SCHEMA.SYSTEM_USERS"; //NON-NLS
  }

  @Override
  public String getSpidQuery() {
    return "call session_id()";
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
