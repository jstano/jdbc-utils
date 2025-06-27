package com.stano.jdbcutils.datasource.util

import com.stano.jdbcutils.datasource.impl.TracingBasicDataSource
import org.apache.commons.dbcp2.BasicDataSource
import spock.lang.Specification

class DataSourceConfigurationPropertiesUtilsSpec extends Specification {
  def "getDataSourceConfigurationProperties should return the information from a BasicDataSource"() {
    def dataSource = Mock(BasicDataSource) {
      getUrl() >> "jdbc:postgresql://server/database"
      getUserName() >> "username"
      getPassword() >> "password"
    }
    def properties = DataSourceConfigurationPropertiesUtils.getDataSourceConfigurationProperties(dataSource).get()

    expect:
    properties.url == "jdbc:postgresql://server/database"
    properties.username == "username"
    properties.password == "password"
    properties.driver == "PGSQL"
  }

  def "getDataSourceConfigurationProperties should return the information from a TracingBasicDataSource"() {
    def dataSource = Mock(TracingBasicDataSource) {
      getUrl() >> "jdbc:postgresql://server/database"
      getUserName() >> "username"
      getPassword() >> "password"
    }
    def properties = DataSourceConfigurationPropertiesUtils.getDataSourceConfigurationProperties(dataSource).get()

    expect:
    properties.url == "jdbc:postgresql://server/database"
    properties.username == "username"
    properties.password == "password"
    properties.driver == "PGSQL"
  }

  def "getDataSourceConfigurationProperties should return the information from a DataSource that has a dataSource field"() {
    def dataSource = Mock(BasicDataSource) {
      getUrl() >> "jdbc:postgresql://server/database"
      getUserName() >> "username"
      getPassword() >> "password"
    }
    def delegatingDataSource = new DataSourceWithDataSourceField(dataSource)
    def properties = DataSourceConfigurationPropertiesUtils.getDataSourceConfigurationProperties(delegatingDataSource).get()

    expect:
    properties.url == "jdbc:postgresql://server/database"
    properties.username == "username"
    properties.password == "password"
    properties.driver == "PGSQL"
  }

  def "call the private constructor so the code coverage is accurate"() {
    expect:
    new DataSourceConfigurationPropertiesUtils()
  }
}
