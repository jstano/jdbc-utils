package com.stano.jdbcutils.datasource

import spock.lang.Specification

class DataSourceConfigurationPropertiesSpec extends Specification {
  def "should be able create a DataSourceConfigurationProperties object and get the values out"() {
    def dataSourceConfigurationProperties = new DataSourceConfigurationProperties(url: 'URL',
                                                                                  username: 'USERNAME',
                                                                                  password: 'PASSWORD',
                                                                                  driver: 'PGSQL',
                                                                                  showSql: true,
                                                                                  autoMigrate: true)
    def dataSourceProperties = dataSourceConfigurationProperties.toDataSourceProperties()

    expect:
    dataSourceConfigurationProperties.isValid()
    dataSourceConfigurationProperties.url == 'URL'
    dataSourceConfigurationProperties.username == 'USERNAME'
    dataSourceConfigurationProperties.password == 'PASSWORD'
    dataSourceConfigurationProperties.driver == 'PGSQL'
    dataSourceConfigurationProperties.showSql
    dataSourceConfigurationProperties.autoMigrate

    dataSourceProperties.url == 'URL'
    dataSourceProperties.username == 'USERNAME'
    dataSourceProperties.password == 'PASSWORD'
    dataSourceProperties.driverType == DriverType.PGSQL
  }

  def "should be able create a DataSourceConfigurationProperties with null username and null password and get a DataSourceProperties object"() {
    def dataSourceConfigurationProperties = new DataSourceConfigurationProperties(url: 'URL', driver: 'PGSQL')
    def dataSourceProperties = dataSourceConfigurationProperties.toDataSourceProperties()

    expect:
    dataSourceConfigurationProperties.isValid()
    dataSourceConfigurationProperties.url == 'URL'
    dataSourceConfigurationProperties.username == null
    dataSourceConfigurationProperties.password == null
    dataSourceConfigurationProperties.driver == 'PGSQL'

    dataSourceProperties.url == 'URL'
    dataSourceProperties.password == null
    dataSourceProperties.driverType == DriverType.PGSQL
  }

  def "should be able create a DataSourceConfigurationProperties with non-null username and null password and get a DataSourceProperties object"() {
    def dataSourceConfigurationProperties = new DataSourceConfigurationProperties(url: 'URL', username: 'username', driver: 'PGSQL')
    def dataSourceProperties = dataSourceConfigurationProperties.toDataSourceProperties()

    expect:
    dataSourceConfigurationProperties.isValid()
    dataSourceConfigurationProperties.url == 'URL'
    dataSourceConfigurationProperties.username == 'username'
    dataSourceConfigurationProperties.password == null
    dataSourceConfigurationProperties.driver == 'PGSQL'

    dataSourceProperties.url == 'URL'
    dataSourceProperties.username == 'username'
    dataSourceProperties.password == null
    dataSourceProperties.driverType == DriverType.PGSQL
  }

  def "should be able create a DataSourceConfigurationProperties with a null driverType and get a DataSourceProperties object"() {
    def dataSourceConfigurationProperties = new DataSourceConfigurationProperties(url: 'jdbc:postgresql://server/database')
    def dataSourceProperties = dataSourceConfigurationProperties.toDataSourceProperties()

    expect:
    dataSourceConfigurationProperties.isValid()
    dataSourceConfigurationProperties.url == 'jdbc:postgresql://server/database'
    dataSourceConfigurationProperties.username == null
    dataSourceConfigurationProperties.password == null
    dataSourceConfigurationProperties.driver == null

    dataSourceProperties.url == 'jdbc:postgresql://server/database'
    dataSourceProperties.username == null
    dataSourceProperties.password == null
    dataSourceProperties.driverType == DriverType.PGSQL
  }

  def "toDataSourceProperties should throw a IllegalArgumentException if the url is null or blank"() {
    def dataSourceConfigurationProperties = new DataSourceConfigurationProperties(url: url)

    when:
    dataSourceConfigurationProperties.toDataSourceProperties()

    then:
    thrown IllegalArgumentException

    where:
    url  | result
    null | null
    ''   | null
    ' '  | null
  }

  def "a DataSourceConfigurationProperties object without a url should not be valid"() {
    def dataSourceConfigurationProperties = new DataSourceConfigurationProperties()

    expect:
    !dataSourceConfigurationProperties.isValid()
  }
}
