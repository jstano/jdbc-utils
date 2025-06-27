package com.stano.jdbcutils.utils;

import java.sql.SQLException;

public interface SQLExecutorWithResult<T> {
  T execute() throws SQLException;
}
