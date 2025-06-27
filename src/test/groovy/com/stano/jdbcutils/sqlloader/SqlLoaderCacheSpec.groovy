package com.stano.jdbcutils.sqlloader

import com.stano.jdbcutils.datasource.DriverType
import com.stano.jdbcutils.sqlloader.impl.SqlLoaderCache
import spock.lang.Specification

import java.sql.Connection
import java.sql.DatabaseMetaData

class SqlLoaderCacheSpec extends Specification {
  def setup() {
    SqlLoaderCache.sqlCache.clear()
  }

  def "should be able to load the proper sql based on the driver type"() {
    def sql = SqlLoaderCache.loadSql("test", driverType, SqlLoaderCacheSpec.class.classLoader)

    expect:
    sql == expectedSql

    where:
    driverType       | expectedSql
    DriverType.MSSQL | "test-mssql"
    DriverType.PGSQL | "test-pgsql"
  }

  def "should throw an IllegalArgumentException if the sql file is not found"() {
    when:
    SqlLoaderCache.loadSql("not-found", DriverType.PGSQL, SqlLoaderCacheSpec.class.classLoader)

    then:
    thrown IllegalStateException
  }

  def "the sqlCache should be populated"() {
    def mssqlMetaData = Mock(DatabaseMetaData)
    mssqlMetaData.getURL() >> "jdbc:sqlserver://server/db"

    def pgsqlMetaData = Mock(DatabaseMetaData)
    pgsqlMetaData.getURL() >> "jdbc:postgresql://server/db"

    def mssqlConnection = Mock(Connection)
    mssqlConnection.getMetaData() >> mssqlMetaData

    def pgsqlConnection = Mock(Connection)
    pgsqlConnection.getMetaData() >> pgsqlMetaData

    SqlLoaderCache.loadSql("test", DriverType.MSSQL, SqlLoaderCacheSpec.class.classLoader)
    SqlLoaderCache.loadSql("test", DriverType.PGSQL, SqlLoaderCacheSpec.class.classLoader)

    expect:
    SqlLoaderCache.sqlCache.get("test-mssql") == "test-mssql"
    SqlLoaderCache.sqlCache.get("test-pgsql") == "test-pgsql"
  }

  def "should read from the cache before reading the file"() {
    def mssqlMetaData = Mock(DatabaseMetaData)
    mssqlMetaData.getURL() >> "jdbc:sqlserver://server/db"

    def pgsqlMetaData = Mock(DatabaseMetaData)
    pgsqlMetaData.getURL() >> "jdbc:postgresql://server/db"

    def mssqlConnection = Mock(Connection)
    mssqlConnection.getMetaData() >> mssqlMetaData

    def pgsqlConnection = Mock(Connection)
    pgsqlConnection.getMetaData() >> pgsqlMetaData

    SqlLoaderCache.putSqlInCache("test", DriverType.MSSQL, "test-mssql-cache")
    SqlLoaderCache.putSqlInCache("test", DriverType.PGSQL, "test-pgsql-cache")

    expect:
    SqlLoaderCache.loadSql("test", DriverType.MSSQL, SqlLoaderCacheSpec.class.classLoader) == "test-mssql-cache"
    SqlLoaderCache.loadSql("test", DriverType.PGSQL, SqlLoaderCacheSpec.class.classLoader) == "test-pgsql-cache"
  }

  def "should get a RuntimeIOException if an IOException is thrown"() {
    def classLoader = Mock(ClassLoader)
    classLoader.getResourceAsStream(_) >> {
      throw new IOException()
    }

    when:
    SqlLoaderCache.loadSql("test", DriverType.PGSQL, classLoader)

    then:
    thrown IllegalStateException
  }

  def "call the private constructor so the coverage is correct"() {
    expect:
    new SqlLoaderCache()
  }
}
