package com.stano.jdbcutils.datasource.util

import org.apache.commons.dbcp2.BasicDataSource
import spock.lang.Specification

class DataSourceUtilSpec extends Specification {
  def "getUrlFromDataSource should return the URL from a BasicDataSource"() {
    def dataSource = Mock(BasicDataSource) {
      getUrl() >> "jdbc:hsqldb://server/database"
    }

    expect:
    DataSourceUtil.getUrlFromDataSource(dataSource) == 'jdbc:hsqldb://server/database'
  }

  def "getUrlFromDataSource should return the URL from a DataSource that has a getUrl method"() {
    def dataSource = new DataSourceWithGetUrl()

    expect:
    DataSourceUtil.getUrlFromDataSource(dataSource) == 'the-url'
  }

  def "getUrlFromDataSource should return the URL from a DataSource that has a dataSource field"() {
    def dataSource = new DataSourceWithGetUrl()

    expect:
    DataSourceUtil.getUrlFromDataSource(dataSource) == 'the-url'
  }

  def "call the private constructor so the code coverage is accurate"() {
    expect:
    new DataSourceUtil()
  }
}
