package com.stano.jdbcutils.datasource.impl

import spock.lang.Specification

class MSSQLDriverSpec extends Specification {

  def "should be able to create an instance of the driver"() {

    def driver = new MSSQLDriver()

    expect:
    driver.jdbcUrlPrefix == "jdbc:sqlserver:"
    driver.driverClass == "com.microsoft.sqlserver.jdbc.SQLServerDriver"
    driver.validationQuery == "SELECT 1"
    driver.getFullUrl("//server/database") == "jdbc:sqlserver://server;databaseName=database"
    driver.getFullUrl("//server/database;ssl=true;enabled=false") == "jdbc:sqlserver://server;databaseName=database;ssl=true;enabled=false"
    driver.getFullUrl("jdbc:sqlserver://server;databaseName=database;ssl=true;enabled=false") == "jdbc:sqlserver://server;databaseName=database;ssl=true;enabled=false"
    driver.supportsClob()
    driver.getDriver().getClass().getName() == "com.microsoft.sqlserver.jdbc.SQLServerDriver"
    driver.spidQuery == "select @@SPID"
    driver.masterDatabaseName == "master"
  }
}
