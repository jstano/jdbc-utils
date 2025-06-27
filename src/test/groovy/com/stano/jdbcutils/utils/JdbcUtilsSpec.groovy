package com.stano.jdbcutils.utils

import com.stano.jdbcutils.datasource.DriverType
import spock.lang.Specification

import java.sql.Connection
import java.sql.SQLException

class JdbcUtilsSpec extends Specification {
  def "safeClose should not throw an exception if the close call works"() {
    def connection = Mock(Connection)

    when:
    JdbcUtils.safeClose(connection)

    then:
    notThrown(Exception)
  }

  def "safeClose should throw a RuntimeSQLException if the close call throws a SQLException"() {
    def connection = Mock(Connection)
    connection.close() >> {
      throw new SQLException("BAD STUFF");
    }

    when:
    JdbcUtils.safeClose(connection)

    then:
    def x = thrown(RuntimeSQLException)
    x.message == "java.sql.SQLException: BAD STUFF"
  }

  def "getSpidForConnection should return a valid spid for in-memory databases"() {
    def connection = driverType.jdbcDriver.openConnection(databaseUrl, "", "")

    when:
    def spid = JdbcUtils.getSpidForConnection(connection)

    then:
    spid != null

    cleanup:
    connection.close()

    where:
    driverType      | databaseUrl
    DriverType.H2   | "mem:test"
    DriverType.HSQL | "mem:test"
  }

  def "getUrlForConnection should return the url for the connection"() {
    def connection = DriverType.HSQL.jdbcDriver.openConnection("mem:test", "", "")

    when:
    def connectionUrl = JdbcUtils.getUrlForConnection(connection)

    then:
    connectionUrl == "jdbc:hsqldb:mem:test"

    cleanup:
    connection.close()
  }

  def "call the private constructor so coverage is accurate"() {
    expect:
    new JdbcUtils() != null
  }
}
