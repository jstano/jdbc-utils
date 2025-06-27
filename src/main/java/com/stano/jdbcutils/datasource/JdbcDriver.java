package com.stano.jdbcutils.datasource;

import com.stano.jdbcutils.utils.RuntimeSQLException;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public interface JdbcDriver {
  String getJdbcUrlPrefix();

  String getDriverClass();

  String getValidationQuery();

  String getSpidQuery();

  boolean supportsClob();

  String getSpidForConnection(Connection connection);

  String getMasterDatabaseName();

  default Driver getDriver() {

    try {
      return (Driver)Class.forName(getDriverClass()).newInstance();
    }
    catch (Exception x) {
      throw new RuntimeException(x);
    }
  }

  default String getFullUrl(String url) {

    String driverPrefix = getJdbcUrlPrefix();

    if (url.startsWith(driverPrefix)) {
      return url;
    }

    return driverPrefix + url;
  }

  default Connection openConnection(String databaseUrl, String userName, String password) throws RuntimeSQLException {

    try {
      return DriverManager.getConnection(getFullUrl(databaseUrl), userName, password);
    }
    catch (SQLException x) {
      throw new RuntimeSQLException(x);
    }
  }

  void createDatabase(String databaseUrl, String userName, String password) throws RuntimeSQLException;
}
