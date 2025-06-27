package com.stano.jdbcutils.datasource.impl


import spock.lang.Specification

import java.sql.Statement

class H2DriverSpec extends Specification {

   def driver = new H2Driver()

   def "should be able to create an instance of the driver and get it's properties"() {

      expect:
      driver.jdbcUrlPrefix == "jdbc:h2:"
      driver.driverClass == "org.h2.Driver"
      driver.validationQuery == "SELECT 1"
      driver.getFullUrl("//server/database") == "jdbc:h2://server/database"
      driver.getFullUrl("jdbc:h2://server/database") == "jdbc:h2://server/database"
      driver.supportsClob()
      driver.getDriver().getClass().getName() == "org.h2.Driver"
      driver.spidQuery == "select session_id()"
      !driver.getMasterDatabaseName()
   }

   def "databaseExists should return true"() {

      expect:
      driver.databaseExists(Mock(Statement), "testdb")
   }

   def "getSpidForConnection should return a value"() {

      def connection = driver.openConnection("mem:testdb", "sa", "")

      expect:
      driver.getSpidForConnection(connection)

      cleanup:
      connection.close()
   }
}
