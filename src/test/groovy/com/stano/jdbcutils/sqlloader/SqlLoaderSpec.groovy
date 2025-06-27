package com.stano.jdbcutils.sqlloader

import com.stano.jdbcutils.datasource.DriverType
import com.stano.jdbcutils.sqlloader.impl.SqlLoaderCache
import org.apache.commons.dbcp2.BasicDataSource
import spock.lang.Specification

import java.sql.Connection
import java.sql.DatabaseMetaData

class SqlLoaderSpec extends Specification {
  def setup() {
    SqlLoaderCache.sqlCache.clear()
  }

  def "should be able to load the proper sql based on the connection type"() {
    def metaData = Mock(DatabaseMetaData)
    metaData.getURL() >> connectionUrl

    def connection = Mock(Connection)
    connection.getMetaData() >> metaData

    def sqlLoader = SqlLoader.with(SqlLoaderSpec, connection)
    def sql = sqlLoader.loadSql("test")

    expect:
    sql == expectedSql

    where:
    connectionUrl                 | expectedSql
    "jdbc:sqlserver://server/db"  | "test-mssql"
    "jdbc:postgresql://server/db" | "test-pgsql"
  }

  def "should be able to load the proper sql based on the dataSource type"() {
    def dataSource = Mock(BasicDataSource)
    dataSource.getUrl() >> connectionUrl

    def sqlLoader = SqlLoader.with(SqlLoaderSpec, dataSource)
    def sql = sqlLoader.loadSql("test")

    expect:
    sql == expectedSql

    where:
    connectionUrl                 | expectedSql
    "jdbc:sqlserver://server/db"  | "test-mssql"
    "jdbc:postgresql://server/db" | "test-pgsql"
  }

  def "should be able to load the proper sql based on the driver type"() {
    def sqlLoader = SqlLoader.with(SqlLoaderSpec, driverType)
    def sql = sqlLoader.loadSql("test")

    expect:
    sql == expectedSql

    where:
    driverType       | expectedSql
    DriverType.MSSQL | "test-mssql"
    DriverType.PGSQL | "test-pgsql"
  }

  def "should be able to load the proper sql based on the default dataSource passed in to the constructor"() {
    def dataSource = Mock(BasicDataSource)
    dataSource.getUrl() >> connectionUrl

    def sqlLoader = SqlLoader.with(SqlLoaderSpec, dataSource)
    def sql = sqlLoader.loadSql("test")

    expect:
    sql == expectedSql

    where:
    connectionUrl                 | expectedSql
    "jdbc:sqlserver://server/db"  | "test-mssql"
    "jdbc:postgresql://server/db" | "test-pgsql"
  }
}
