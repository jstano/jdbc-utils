package com.stano.jdbcutils.datasource.impl

import spock.lang.Specification

class PGSQLDriverSpec extends Specification {

  def "should be able to create an instance of the driver"() {

    def driver = new PGSQLDriver()

    expect:
    driver.jdbcUrlPrefix == "jdbc:postgresql:"
    driver.driverClass == "org.postgresql.Driver"
    driver.validationQuery == "SELECT 1"
    driver.getFullUrl("//server/database") == "jdbc:postgresql://server/database"
    driver.getFullUrl("jdbc:postgresql://server/database") == "jdbc:postgresql://server/database"
    !driver.supportsClob()
    driver.getDriver().getClass().getName() == "org.postgresql.Driver"
    driver.masterDatabaseName == "postgres"
    driver.spidQuery == "select pg_backend_pid()"
  }
}
