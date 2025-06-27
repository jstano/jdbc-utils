package com.stano.jdbcutils.datasource

import groovy.sql.Sql
import spock.lang.Specification

class EmbeddedDataSourceFactorySpec extends Specification {

   def "getEmbeddedDataSource for H2 should work as expected"() {

      def dataSource = EmbeddedDataSourceFactory.getEmbeddedDataSource(DriverType.H2, "test_h2")
      def sql = new Sql(dataSource)
      def count = executeSql(sql)

      expect:
      count == 1

      cleanup:
      dataSource.shutdown()
   }

   def "getEmbeddedDataSource for HSQL should work as expected"() {

      def dataSource = EmbeddedDataSourceFactory.getEmbeddedDataSource(DriverType.HSQL, "test_hsql")
      def sql = new Sql(dataSource)
      def count = executeSql(sql)

      expect:
      count == 1

      cleanup:
      dataSource.shutdown()
   }

   def "getEmbeddedDataSource should throw an IllegalArgumentException if the database is not an embedded database"() {

      when:
      EmbeddedDataSourceFactory.getEmbeddedDataSource(driverType, databaseName)

      then:
      thrown IllegalStateException

      where:
      driverType       | databaseName
      DriverType.MSSQL | 'test_mssql'
      DriverType.MYSQL | 'test_mysql'
      DriverType.PGSQL | 'test_pgsql'
   }

   def "call the private constructor so the code coverage is accurate"() {

      expect:
      new EmbeddedDataSourceFactory()
   }

   private int executeSql(Sql sql) {
      sql.execute("create table test(id int,name varchar(100))")
      sql.execute("insert into test values (1,'One')")
      return (int)sql.rows("select count(*) as count from test").get(0).get("count")
   }
}
