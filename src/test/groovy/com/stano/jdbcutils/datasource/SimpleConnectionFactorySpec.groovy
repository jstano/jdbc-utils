package com.stano.jdbcutils.datasource

import spock.lang.Specification

class SimpleConnectionFactorySpec extends Specification {

  def "should be able to create a connection"() {

    def url = "mem:"
    def userName = "username"
    def password = "password"
    def driverType = DriverType.H2

    when:
    def connection = SimpleConnectionFactory.createConnection(url, userName, password, driverType)

    then:
    connection

    cleanup:
    connection.close()
  }

  def "should be able to create a connection with specifying a driver"() {

    def url = "jdbc:h2:mem:"
    def userName = "username"
    def password = "password"

    when:
    def connection = SimpleConnectionFactory.createConnection(url, userName, password)

    then:
    connection

    cleanup:
    connection.close()
  }

  def "call the private constructor so coverage is accurate"() {

    expect:
    new SimpleConnectionFactory() != null
  }
}
