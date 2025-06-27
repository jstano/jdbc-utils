package com.stano.jdbcutils.datasource;

import com.stano.jdbcutils.utils.JdbcUtils;
import com.stano.jdbcutils.utils.TransactionalExecutor;
import io.opentelemetry.instrumentation.jdbc.internal.OpenTelemetryConnection;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DataSourceTester {
  private DataSource dataSource;

  private DataSourceTester() {
    dataSource = DataSourceFactory.getDataSource("//ctx-d-sql-001/testds;encrypt=true;trustServerCertificate=true",
                                                 "dbuser",
                                                 "db~s3r",
                                                 DriverType.MSSQL);
  }

  private void execute() throws SQLException {
    try (Connection connection = dataSource.getConnection()) {
      OpenTelemetryConnection openTelemetryConnection = (OpenTelemetryConnection)connection;
      Connection c = openTelemetryConnection.unwrap(Connection.class);
      System.out.println(System.identityHashCode(c));
      System.out.println(c.toString());
      System.out.printf("spid: %s%n", JdbcUtils.getSpidForConnection(connection));

      TransactionalExecutor.withConnection(connection).execute(() -> {
        try (Statement statement = connection.createStatement()) {
          statement.execute("truncate table Test");
        }

        try (PreparedStatement statement = connection.prepareStatement("insert into Test (Value) values (?)")) {
          for (int i = 1; i <= 1000; i++) {
            statement.clearParameters();
            statement.setInt(1, i);
            statement.executeUpdate();

            sleep();
          }
        }
      });
    }
  }

  private void sleep() {
    try {
      Thread.sleep(5000L);
    }
    catch (InterruptedException ignored) {
    }
  }

  public static void main(String[] args) {
    DataSourceTester dataSourceTester = new DataSourceTester();

    try {
      dataSourceTester.execute();
    }
    catch (Throwable x) {
      try {
        dataSourceTester.execute();
      }
      catch (SQLException x2) {
        x2.printStackTrace();
      }

      x.printStackTrace();
    }
  }
}
