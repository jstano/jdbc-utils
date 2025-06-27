package com.stano.jdbcutils.datasource.impl;

import com.stano.jdbcutils.datasource.DriverType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.sql.Statement;

public class H2EmbeddedDataSource extends EmbeddedDataSource {
  private static final Logger LOGGER = LoggerFactory.getLogger(H2EmbeddedDataSource.class);

  private static final String URL_TEMPLATE = "mem:%s;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=false";

  public H2EmbeddedDataSource(String databaseName) {
    super(DriverType.H2, String.format(URL_TEMPLATE, databaseName));
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
