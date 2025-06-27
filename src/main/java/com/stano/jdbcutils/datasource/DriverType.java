package com.stano.jdbcutils.datasource;

import com.stano.jdbcutils.datasource.impl.H2Driver;
import com.stano.jdbcutils.datasource.impl.HSQLDriver;
import com.stano.jdbcutils.datasource.impl.MSSQLDriver;
import com.stano.jdbcutils.datasource.impl.MySQLDriver;
import com.stano.jdbcutils.datasource.impl.PGSQLDriver;
import com.stano.jdbcutils.datasource.util.DataSourceUtil;
import com.stano.jdbcutils.utils.RuntimeSQLException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public enum DriverType {
  H2(new H2Driver()),
  HSQL(new HSQLDriver()),
  MSSQL(new MSSQLDriver()),
  MYSQL(new MySQLDriver()),
  PGSQL(new PGSQLDriver());

  public static DriverType fromConnection(Connection connection) throws RuntimeSQLException {
    try {
      return fromConnectionUrl(connection.getMetaData().getURL().toLowerCase());
    }
    catch (SQLException x) {
      throw new RuntimeSQLException(x);
    }
  }

  public static DriverType fromDataSource(DataSource dataSource) {
    String url = DataSourceUtil.getUrlFromDataSource(dataSource);

    if (url != null) {
      return fromConnectionUrl(url.toLowerCase());
    }

    throw new IllegalArgumentException(String.format("Unable to determine the driver type for the data source '%s'",
                                                     dataSource.getClass().getName()));
  }

  public static DriverType fromConnectionUrl(String connectionUrl) {
    for (DriverType driverType : values()) {
      if (connectionUrl.startsWith(driverType.jdbcDriver.getJdbcUrlPrefix())) {
        return driverType;
      }
    }

    throw new IllegalArgumentException(String.format("Unable to determine the driver type for the connection url '%s'",
                                                     connectionUrl));
  }

  private final JdbcDriver jdbcDriver;

  public JdbcDriver getJdbcDriver() {
    return jdbcDriver;
  }

  DriverType(JdbcDriver jdbcDriver) {
    this.jdbcDriver = jdbcDriver;

    try {
      Class.forName(this.jdbcDriver.getDriverClass());
    }
    catch (ClassNotFoundException x) {
      throw new RuntimeException(x);
    }
  }
}
