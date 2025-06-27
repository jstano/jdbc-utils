package com.stano.jdbcutils.datasource;

import com.stano.jdbcutils.utils.RuntimeSQLException;
import com.stano.jdbcutils.utils.SqlUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public class SimpleDataSourceFactory {
  public Optional<DataSource> getDataSource(String name) {
    try {
      Connection connection = null;
      try (Statement statement = connection.createStatement()) {
        try (ResultSet rs = statement.executeQuery(String.format("select * from data_source where name = '%s'", SqlUtils.escapeSqlString(name)))) {
          if (rs.next()) {
            String jdbcUrl = rs.getString("jdbc_url");
            String username = rs.getString("username");
            String password = rs.getString("password");

            return Optional.of(DataSourceFactory.getDataSource(jdbcUrl, username, password));
          }
        }
      }

      return Optional.empty();
    }
    catch (SQLException x) {
      throw new RuntimeSQLException(x);
    }
  }
}
