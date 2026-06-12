package com.stano.jdbcutils.datasource

import spock.lang.Specification

class DataSourceFactorySpec extends Specification {
  def "getDataSource with the driverType should return the dataSource properly configured"() {
    def dataSource = DataSourceFactory.getDataSource("//server/database", "username", "password", DriverType.H2)

    expect:
    dataSource.url == "jdbc:h2://server/database"
    dataSource.userName == "username"
    dataSource.password == "password"
    dataSource.driverClassName == "org.h2.Driver"
  }

  def "getDataSource without the driverType should return the dataSource properly configured"() {
    def dataSource = DataSourceFactory.getDataSource("jdbc:h2://server/database", "username", "password")

    expect:
    dataSource.url == "jdbc:h2://server/database"
    dataSource.userName == "username"
    dataSource.password == "password"
    dataSource.driverClassName == "org.h2.Driver"
  }

  def "getDataSource should return the same object when called multiple times with the same parameters"() {
    def dataSource1 = DataSourceFactory.getDataSource("//server/database", "username", "password", DriverType.H2)
    def dataSource2 = DataSourceFactory.getDataSource("//server/database", "username", "password", DriverType.H2)

    expect:
    dataSource1 == dataSource2
  }

  def "getDataSource should return different objects when called multiple times with a different driverType"() {
    def dataSource1 = DataSourceFactory.getDataSource("//server/database", "username", "password", DriverType.H2)
    def dataSource2 = DataSourceFactory.getDataSource("//server/database", "username", "password", DriverType.MSSQL)

    expect:
    dataSource1 != dataSource2
  }

  def "getDataSource should return different objects when called multiple times with a different url"() {
    def dataSource1 = DataSourceFactory.getDataSource("//server/database", "username", "password", DriverType.H2)
    def dataSource2 = DataSourceFactory.getDataSource("//server/database2", "username", "password", DriverType.H2)

    expect:
    dataSource1 != dataSource2
  }

  def "getDataSource should return different objects when called multiple times with a different username"() {
    def dataSource1 = DataSourceFactory.getDataSource("//server/database", "username", "password", DriverType.H2)
    def dataSource2 = DataSourceFactory.getDataSource("//server/database", "username2", "password", DriverType.H2)

    expect:
    dataSource1 != dataSource2
  }

  def "getDataSource should return different objects when called multiple times with a different password"() {
    def dataSource1 = DataSourceFactory.getDataSource("//server/database", "username", "password", DriverType.H2)
    def dataSource2 = DataSourceFactory.getDataSource("//server/database", "username", "password2", DriverType.H2)

    expect:
    dataSource1 != dataSource2
  }

  def "test private constructor so coverage is 100%"() {
    expect:
    new DataSourceFactory()
  }
}
