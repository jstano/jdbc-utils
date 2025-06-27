package com.stano.jdbcutils.datasource.impl

import com.stano.jdbcutils.datasource.DataSourceProperties
import com.stano.jdbcutils.datasource.DriverType
import spock.lang.Specification

class DbcpDataSourceFactorySpec extends Specification {
  def "should be able to create an H2 datasource and run a query"() {
    def dbcpDataSourceFactory = new DbcpDataSourceFactory()
    def dataSource = dbcpDataSourceFactory.createPooledDataSource(new DataSourceProperties("//server/database", "username", "password", DriverType.HSQL))

    expect:
    dataSource instanceof TracingBasicDataSource
    dataSource.driverClassName == "org.hsqldb.jdbcDriver"
    dataSource.url == DriverType.HSQL.jdbcDriver.getFullUrl("//server/database")
    dataSource.userName == "username"
    dataSource.password == "password"
    dataSource.validationQuery == "SELECT 1 FROM INFORMATION_SCHEMA.SYSTEM_USERS"
  }
}
