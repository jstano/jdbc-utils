package com.stano.jdbcutils.datasource.util;

import java.sql.Connection;
import java.sql.SQLException;

public final class ConnectionUtil {
  public static String getUrl(Connection connection) {
    try {
      return connection.getMetaData().getURL();
    }
    catch (SQLException ignored) {
    }

    return null;
  }

  private ConnectionUtil() {
  }
}
