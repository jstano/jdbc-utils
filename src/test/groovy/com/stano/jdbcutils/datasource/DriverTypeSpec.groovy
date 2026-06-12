package com.stano.jdbcutils.datasource

import com.stano.jdbcutils.datasource.impl.H2Driver

import com.stano.jdbcutils.datasource.impl.MSSQLDriver
import com.stano.jdbcutils.datasource.impl.MySQLDriver
import com.stano.jdbcutils.datasource.impl.PGSQLDriver
import com.stano.jdbcutils.utils.RuntimeSQLException
import org.apache.commons.dbcp2.BasicDataSource
import spock.lang.Specification

import java.sql.Connection
import java.sql.DatabaseMetaData
import java.sql.SQLException

class DriverTypeSpec extends Specification {
  def testDriverType() {
    expect:
    DriverType.values().size() == 4
    DriverType.H2.jdbcDriver instanceof H2Driver
    DriverType.MSSQL.jdbcDriver instanceof MSSQLDriver
    DriverType.MYSQL.jdbcDriver instanceof MySQLDriver
    DriverType.PGSQL.jdbcDriver instanceof PGSQLDriver
  }

  def "fromConnection should return the correct driver type"() {
    def connection = Mock(Connection) {
      getMetaData() >> Mock(DatabaseMetaData) {
        getURL() >> "jdbc:h2://server/database"
      }
    }

    expect:
    DriverType.fromConnection(connection) == DriverType.H2
  }

  def "fromConnection should throw a RuntimeSQLException if a SQLException occurs"() {
    def connection = Mock(Connection) {
      getMetaData() >> {
        throw new SQLException()
      }
    }

    when:
    DriverType.fromConnection(connection)

    then:
    thrown RuntimeSQLException
  }

  def "fromDataSource should return the DriverType based on the url of the DataSource"() {
    def dataSource = Mock(BasicDataSource) {
      getUrl() >> "jdbc:h2://server/database"
    }

    expect:
    DriverType.fromDataSource(dataSource) == DriverType.H2
  }

  def "fromDataSource should throw an IllegalArgumentException of the url cannot be determined"() {
    def dataSource = Mock(BasicDataSource) {
      getUrl() >> null
      getConnection() >> Mock(Connection) {
        getMetaData() >> Mock(DatabaseMetaData) {
          getURL() >> null
        }
      }
    }

    when:
    DriverType.fromDataSource(dataSource)

    then:
    thrown(IllegalArgumentException)
  }

  def "fromConnectionUrl should return the correct DriverType for all the supported urls"() {
    expect:
    DriverType.fromConnectionUrl(url) == expectedDriverType

    where:
    url                                              | expectedDriverType
    "jdbc:h2://server/database"                      | DriverType.H2
    "jdbc:sqlserver://server/;databaseName=database" | DriverType.MSSQL
    "jdbc:mysql://server/database"                   | DriverType.MYSQL
    "jdbc:postgresql://server/database"              | DriverType.PGSQL
  }

  def "passing an invalid url to the fromConnectionUrl method will cause an exception to be thrown"() {
    when:
    DriverType.fromConnectionUrl("ABC")

    then:
    thrown IllegalArgumentException
  }
}
