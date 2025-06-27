package com.stano.jdbcutils.datasource

import spock.lang.Specification

class DataSourcePropertiesSpec extends Specification {
  def "should be able to create a DataSourceProperties object and get the values out"() {
    def dataSourceProperties = new DataSourceProperties("//server/database", "username", "password", DriverType.HSQL)

    expect:
    dataSourceProperties.url == "//server/database"
    dataSourceProperties.username == "username"
    dataSourceProperties.password == "password"
    dataSourceProperties.driverType == DriverType.HSQL
  }

  def "should be able to create a DataSourceProperties object with only a URL"() {
    def dataSourceProperties = new DataSourceProperties("jdbc:hsqldb:mem:test")

    expect:
    dataSourceProperties.url == "jdbc:hsqldb:mem:test"
    dataSourceProperties.username == null
    dataSourceProperties.password == null
    dataSourceProperties.driverType == DriverType.HSQL
  }

  def "test equals based on changes to the fields"() {
    def dataSourceProperties1 = new DataSourceProperties(url1, userName1, password1, driverType1)
    def dataSourceProperties2 = new DataSourceProperties(url2, userName2, password2, driverType2)

    expect:
    dataSourceProperties1.equals(dataSourceProperties2) == expectedResult

    where:
    url1                | userName1  | password1  | driverType1     | url2                 | userName2   | password2   | driverType2      | expectedResult
    "//server/database" | "userName" | "password" | DriverType.HSQL | "//server/database"  | "userName"  | "password"  | DriverType.HSQL  | true
    "//server/database" | "userName" | "password" | DriverType.HSQL | "//server/database2" | "userName"  | "password"  | DriverType.HSQL  | false
    "//server/database" | "userName" | "password" | DriverType.HSQL | "//server/database"  | "userName2" | "password"  | DriverType.HSQL  | false
    "//server/database" | "userName" | "password" | DriverType.HSQL | "//server/database"  | "userName"  | "password2" | DriverType.HSQL  | false
    "//server/database" | "userName" | "password" | DriverType.HSQL | "//server/database"  | "userName"  | "password"  | DriverType.MSSQL | false
  }

  def "test equals - other cases"() {
    def dataSourceProperties1 = new DataSourceProperties("//server/database", "username", "password", DriverType.HSQL)
    def dataSourceProperties2 = new DataSourceProperties("//server/database", "username", "password", DriverType.HSQL)

    expect:
    dataSourceProperties1.equals(dataSourceProperties1)
    dataSourceProperties1.equals(dataSourceProperties2)
    dataSourceProperties2.equals(dataSourceProperties1)
    !dataSourceProperties1.equals("ABC")
  }

  def "test hashCode"() {
    def dataSourceProperties1 = new DataSourceProperties("//server/database", "username", "password", DriverType.HSQL)
    def dataSourceProperties2 = new DataSourceProperties("//server/database", "username", "password", DriverType.HSQL)
    def dataSourceProperties3 = new DataSourceProperties("//server/database2", "username", "password", DriverType.HSQL)
    def dataSourceProperties4 = new DataSourceProperties("//server/database", "username2", "password", DriverType.HSQL)
    def dataSourceProperties5 = new DataSourceProperties("//server/database", "username", "password2", DriverType.HSQL)
    def dataSourceProperties6 = new DataSourceProperties("//server/database", "username", "password", DriverType.MSSQL)

    expect:
    dataSourceProperties1.hashCode() == dataSourceProperties2.hashCode()
    dataSourceProperties1.hashCode() == dataSourceProperties2.hashCode()
    dataSourceProperties1.hashCode() != dataSourceProperties3.hashCode()
    dataSourceProperties1.hashCode() != dataSourceProperties4.hashCode()
    dataSourceProperties1.hashCode() != dataSourceProperties5.hashCode()
    dataSourceProperties1.hashCode() != dataSourceProperties6.hashCode()
  }

  def "equals and hashCode should work properly"() {
    def dataSourceProperties1 = new DataSourceProperties("URL", "USERNAME", "PASSWORD", DriverType.PGSQL)
    def dataSourceProperties2 = new DataSourceProperties("URL", "USERNAME", "PASSWORD", DriverType.PGSQL)
    def dataSourceProperties3a = new DataSourceProperties("URL3", "USERNAME", "PASSWORD", DriverType.PGSQL)
    def dataSourceProperties3b = new DataSourceProperties("URL", "USERNAME3", "PASSWORD", DriverType.PGSQL)
    def dataSourceProperties3c = new DataSourceProperties("URL", "USERNAME", "PASSWORD3", DriverType.PGSQL)
    def dataSourceProperties3d = new DataSourceProperties("URL", "USERNAME", "PASSWORD", DriverType.MYSQL)

    expect:
    dataSourceProperties1.url == "URL"
    dataSourceProperties1.username == "USERNAME"
    dataSourceProperties1.password == "PASSWORD"
    dataSourceProperties1.driverType == DriverType.PGSQL

    dataSourceProperties1.equals(dataSourceProperties2)
    dataSourceProperties1.hashCode() == dataSourceProperties2.hashCode()

    !dataSourceProperties1.equals(dataSourceProperties3a)
    dataSourceProperties1.hashCode() != dataSourceProperties3a.hashCode()

    !dataSourceProperties1.equals(dataSourceProperties3b)
    dataSourceProperties1.hashCode() != dataSourceProperties3b.hashCode()

    !dataSourceProperties1.equals(dataSourceProperties3c)
    dataSourceProperties1.hashCode() != dataSourceProperties3c.hashCode()

    !dataSourceProperties1.equals(dataSourceProperties3d)
    dataSourceProperties1.hashCode() != dataSourceProperties3d.hashCode()
  }

  def "if the driver type is not specified, try and determine it from the url"() {
    def dataSourceProperties1 = new DataSourceProperties("jdbc:h2://server/database", "USERNAME", "PASSWORD")
    def dataSourceProperties2 = new DataSourceProperties("jdbc:hsqldb://server/database", "USERNAME", "PASSWORD", null)

    expect:
    dataSourceProperties1.driverType == DriverType.H2
    dataSourceProperties2.driverType == DriverType.HSQL
  }
}
