package com.stano.jdbcutils.datasource.impl


import spock.lang.Specification

class MySQLDriverSpec extends Specification {

  def "should be able to create an instance of the driver"() {

    def driver = new MySQLDriver()

    expect:
    driver.jdbcUrlPrefix == "jdbc:mysql:"
    driver.driverClass == "com.mysql.cj.jdbc.Driver"
    driver.validationQuery == "SELECT 1"
    driver.getFullUrl("//server/database") == "jdbc:mysql://server/database"
    driver.getFullUrl("jdbc:mysql://server/database") == "jdbc:mysql://server/database"
    !driver.supportsClob()
    driver.getDriver().getClass().getName() == "com.mysql.cj.jdbc.Driver"
    driver.spidQuery == "select connection_id()"
    driver.masterDatabaseName == "mysql"
  }
}
