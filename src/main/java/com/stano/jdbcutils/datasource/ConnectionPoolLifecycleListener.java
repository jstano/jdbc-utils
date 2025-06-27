package com.stano.jdbcutils.datasource;

import java.sql.Connection;

public interface ConnectionPoolLifecycleListener {
  void afterConnectionAcquired(Connection connection);

  void beforeConnectionReleased(Connection connection);
}
