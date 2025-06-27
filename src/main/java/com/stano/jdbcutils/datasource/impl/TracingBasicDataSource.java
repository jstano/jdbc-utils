package com.stano.jdbcutils.datasource.impl;

import io.opentelemetry.instrumentation.jdbc.datasource.OpenTelemetryDataSource;
import org.apache.commons.dbcp2.BasicDataSource;

public class TracingBasicDataSource extends OpenTelemetryDataSource {
  private final BasicDataSource dataSource;

  public TracingBasicDataSource(BasicDataSource dataSource) {
    super(dataSource);

    this.dataSource = dataSource;
  }

  public String getUrl() {
    return dataSource.getUrl();
  }

  public String getUserName() {
    return dataSource.getUserName();
  }

  public String getPassword() {
    return dataSource.getPassword();
  }

  public String getDriverClassName() {
    return dataSource.getDriverClassName();
  }

  public String getValidationQuery() {
    return dataSource.getValidationQuery();
  }

  public boolean isClosed() {
    return dataSource.isClosed();
  }
}
