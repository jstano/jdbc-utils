package com.stano.jdbcutils.datasource.util

import spock.lang.Specification

import java.sql.Connection
import java.sql.DatabaseMetaData

class ConnectionUtilSpec extends Specification {

  def "getUrl should return the URL from the MetaData for the connection"() {

    def metaData = Mock(DatabaseMetaData)
    metaData.getURL() >> "the-url"

    def connection = Mock(Connection)
    connection.getMetaData() >> metaData

    expect:
    ConnectionUtil.getUrl(connection) == 'the-url'
  }

  def "call the private constructor so the code coverage is accurate"() {

    expect:
    new ConnectionUtil()
  }
}
