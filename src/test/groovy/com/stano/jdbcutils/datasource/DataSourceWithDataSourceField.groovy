package com.stano.jdbcutils.datasource

import org.hsqldb.jdbc.JDBCDataSource

import javax.sql.DataSource
import java.sql.Connection
import java.sql.SQLException
import java.sql.SQLFeatureNotSupportedException
import java.util.logging.Logger

class DataSourceWithDataSourceField implements DataSource {
   private DataSource dataSource = new JDBCDataSource(url: "jdbc:hsqldb://server/database")

   @Override
   Connection getConnection() throws SQLException {
      return null
   }

   @Override
   Connection getConnection(String username, String password) throws SQLException {
      return null
   }

   @Override
   PrintWriter getLogWriter() throws SQLException {
      return null
   }

   @Override
   void setLogWriter(PrintWriter out) throws SQLException {
   }

   @Override
   void setLoginTimeout(int seconds) throws SQLException {
   }

   @Override
   int getLoginTimeout() throws SQLException {
      return 0
   }

   @Override
   Logger getParentLogger() throws SQLFeatureNotSupportedException {
      return null
   }

   @Override
   def <T> T unwrap(Class<T> iface) throws SQLException {
      return null
   }

   @Override
   boolean isWrapperFor(Class<?> iface) throws SQLException {
      return false
   }
}
