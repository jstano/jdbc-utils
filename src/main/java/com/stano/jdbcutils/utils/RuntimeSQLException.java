package com.stano.jdbcutils.utils;

import java.sql.SQLException;

public class RuntimeSQLException extends RuntimeException {
  public RuntimeSQLException(SQLException cause) {
    super(cause);
  }

  public RuntimeSQLException(Throwable cause) {
    super(cause);
  }
}
