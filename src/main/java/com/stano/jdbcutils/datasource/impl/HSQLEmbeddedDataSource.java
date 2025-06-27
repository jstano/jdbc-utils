package com.stano.jdbcutils.datasource.impl;

import com.stano.jdbcutils.datasource.DriverType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.sql.Statement;

public class HSQLEmbeddedDataSource extends EmbeddedDataSource {
  private static final Logger LOGGER = LoggerFactory.getLogger(HSQLEmbeddedDataSource.class);

  public HSQLEmbeddedDataSource(String databaseName) {
    super(DriverType.HSQL, "mem:" + databaseName);
  }

  public void shutdown() {
    try (Statement stmt = getConnection().createStatement()) {
      stmt.execute("SHUTDOWN");
    }
    catch (SQLException x) {
      if (LOGGER.isWarnEnabled()) {
        LOGGER.warn("Could not shutdown embedded database", x);
      }
    }
  }
}
