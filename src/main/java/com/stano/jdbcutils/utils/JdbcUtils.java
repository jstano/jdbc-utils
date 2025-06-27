package com.stano.jdbcutils.utils;

import com.stano.jdbcutils.datasource.DriverType;

import java.sql.Connection;
import java.sql.SQLException;

public final class JdbcUtils {
  public static void safeRollback(Connection connection) {
    try {
      connection.rollback();
    }
    catch (SQLException ignored) {
    }
  }

  public static void safeClose(Connection connection) {
    try {
      connection.close();
    }
    catch (SQLException x) {
      throw new RuntimeSQLException(x);
    }
  }

  public static String getSpidForConnection(Connection connection) {
    return DriverType.fromConnection(connection).getJdbcDriver().getSpidForConnection(connection);
  }

  public static String getUrlForConnection(Connection connection) {
    try {
      return connection.getMetaData().getURL();
    }
    catch (SQLException x) {
      throw new RuntimeSQLException(x);
    }
  }

  private JdbcUtils() {
  }
}
