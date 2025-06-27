package com.stano.jdbcutils.datasource.impl

import com.stano.jdbcutils.datasource.DriverType
import com.stano.jdbcutils.datasource.EmbeddedDataSourceFactory
import groovy.sql.Sql
import spock.lang.Specification

import java.sql.SQLFeatureNotSupportedException

class EmbeddedDataSourceSpec extends Specification {
  def "the EmbeddedDataSource should work"() {
    def dataSource = EmbeddedDataSourceFactory.getEmbeddedDataSource(DriverType.H2, "test_h2")

    def sql = new Sql(dataSource)
    sql.execute("create table test (id int, name varchar(100))")
    sql.execute("insert into test values (1,'one')")
    def count = sql.rows("select count(*) as count from test").get(0).get("count")

    expect:
    count == 1

    cleanup:
    dataSource.shutdown()
  }

  def "the EmbeddedDataSource should work 2"() {
    def dataSource = EmbeddedDataSourceFactory.getEmbeddedDataSource(DriverType.H2, "test_h2")

    def sql = new Sql(dataSource)
    sql.execute("create table test (id int, name varchar(100))")
    sql.execute("insert into test values (1,'one')")
    def count = sql.rows("select count(*) as count from test").get(0).get("count")

    expect:
    count == 1

    cleanup:
    dataSource.shutdown()
  }

  def "unwrap should throw a SQLFeatureNotSupportedException"() {
    def dataSource = EmbeddedDataSourceFactory.getEmbeddedDataSource(DriverType.H2, "test_h2")

    when:
    dataSource.unwrap(String.class)

    then:
    thrown SQLFeatureNotSupportedException
  }

  def "isWrapperFor should throw a SQLFeatureNotSupportedException"() {
    def dataSource = EmbeddedDataSourceFactory.getEmbeddedDataSource(DriverType.H2, "test_h2")

    when:
    dataSource.isWrapperFor(String.class)

    then:
    thrown SQLFeatureNotSupportedException
  }

  def "should be able to set and get the logWriter"() {
    def dataSource = EmbeddedDataSourceFactory.getEmbeddedDataSource(DriverType.H2, "test_h2")
    def logWriter = Mock(PrintWriter)

    dataSource.logWriter = logWriter

    expect:
    dataSource.logWriter == logWriter
  }

  def "getLoginTimeout should return 0"() {
    def dataSource = EmbeddedDataSourceFactory.getEmbeddedDataSource(DriverType.H2, "test_h2")

    expect:
    dataSource.loginTimeout == 0
  }

  def "getParentLogger should return null"() {
    def dataSource = EmbeddedDataSourceFactory.getEmbeddedDataSource(DriverType.H2, "test_h2")

    expect:
    dataSource.parentLogger == null
  }
}
