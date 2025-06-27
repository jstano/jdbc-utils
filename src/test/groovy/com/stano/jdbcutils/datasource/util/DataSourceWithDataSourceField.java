package com.stano.jdbcutils.datasource.util;

import com.stano.jdbcutils.datasource.ProxyDataSource;

import javax.sql.DataSource;

public class DataSourceWithDataSourceField extends ProxyDataSource {
  private DataSource dataSource;

  public DataSourceWithDataSourceField(DataSource targetDataSource) {
    super(targetDataSource);

    this.dataSource = targetDataSource;
  }
}
