package com.stano.jdbcutils.datasource.impl


import spock.lang.Specification

import java.sql.Statement

class HSQLDriverSpec extends Specification {

   def driver = new HSQLDriver()

   def "should be able to create an instance of the driver and get it's properties"() {

      expect:
      driver.jdbcUrlPrefix == "jdbc:hsqldb:"
      driver.driverClass == "org.hsqldb.jdbcDriver"
      driver.validationQuery == "SELECT 1 FROM INFORMATION_SCHEMA.SYSTEM_USERS"
      driver.getFullUrl("//server/database") == "jdbc:hsqldb://server/database"
      driver.getFullUrl("jdbc:hsqldb://server/database") == "jdbc:hsqldb://server/database"
      driver.supportsClob()
      driver.getDriver().getClass().getName() == "org.hsqldb.jdbcDriver"
      driver.spidQuery == "call session_id()"
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
