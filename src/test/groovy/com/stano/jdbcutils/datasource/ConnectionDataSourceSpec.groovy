package com.stano.jdbcutils.datasource

import groovy.sql.Sql
import spock.lang.Specification

class ConnectionDataSourceSpec extends Specification {

   def "getDataSource(Connection) should not close the connection"() {

      def embeddedDataSource = EmbeddedDataSourceFactory.getEmbeddedDataSource(DriverType.HSQL, "test")
      def dataSource = DataSourceFactory.getDataSource(embeddedDataSource.connection)
      def sql = new Sql(dataSource.connection)

      sql.execute("create table test (id int)")
      sql.execute("insert into test values (1)")
      def result = sql.firstRow("select id from test")
      dataSource.connection.close()

      expect:
      result.get('id') == 1
      !dataSource.connection.closed

      cleanup:
      embeddedDataSource.shutdown()
   }

   def "getDataSourceOwnConnection(Connection) should close the connection"() {

      def embeddedDataSource = EmbeddedDataSourceFactory.getEmbeddedDataSource(DriverType.HSQL, "test")
      def dataSource = DataSourceFactory.getDataSourceOwnsConnection(embeddedDataSource.connection)
      def sql = new Sql(dataSource.connection)

      sql.execute("create table test (id int)")
      sql.execute("insert into test values (1)")
      def result = sql.firstRow("select id from test")
      dataSource.connection.close()

      expect:
      result.get('id') == 1
      dataSource.connection.closed

      cleanup:
      embeddedDataSource.shutdown()
   }
}
