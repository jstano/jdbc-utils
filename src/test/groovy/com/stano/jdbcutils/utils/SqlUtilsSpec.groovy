package com.stano.jdbcutils.utils

import spock.lang.Specification

import java.sql.ResultSet
import java.sql.ResultSetMetaData
import java.sql.Types

class SqlUtilsSpec extends Specification {

   def "fixSqlString should escape all quote characters in the string"() {

      expect:
      SqlUtils.escapeSqlString("abc") == "abc"
      SqlUtils.escapeSqlString("abc'xyz'123") == "abc''xyz''123"
   }

   def "quoteSqlString should properly quote strings, including ones with embedded quotes in them"() {

      expect:
      SqlUtils.quoteSqlString("abc") == "'abc'"
      SqlUtils.quoteSqlString("abc'xyz'123") == "'abc''xyz''123'"
   }

   def "getColumnType(resultSet,name) should return the correct type"() {

      def metaData = Mock(ResultSetMetaData)
      metaData.getColumnType(1) >> Types.VARCHAR
      metaData.getColumnCount() >> 1
      metaData.getColumnName(1) >> "abc"

      def resultSet = Mock(ResultSet)
      resultSet.getMetaData() >> metaData

      expect:
      SqlUtils.getColumnType(resultSet, "abc") == SqlType.STRING
   }

   def "getColumnType(resultSet,columnIndex) should return the correct type"() {

      def metaData = Mock(ResultSetMetaData)
      metaData.getColumnType(12) >> Types.VARCHAR

      def resultSet = Mock(ResultSet)
      resultSet.getMetaData() >> metaData

      expect:
      SqlUtils.getColumnType(resultSet, 12) == SqlType.STRING
   }

   def "getColumnIndex(resultSet,name) should return the correct index"() {

      def metaData = Mock(ResultSetMetaData)
      metaData.getColumnType(12) >> Types.VARCHAR
      metaData.getColumnCount() >> 3
      metaData.getColumnName(1) >> "abc"
      metaData.getColumnName(2) >> "lmn"
      metaData.getColumnName(3) >> "xyz"

      def resultSet = Mock(ResultSet)
      resultSet.getMetaData() >> metaData

      expect:
      SqlUtils.getColumnIndex(resultSet, "abc") == 1
      SqlUtils.getColumnIndex(resultSet, "lmn") == 2
      SqlUtils.getColumnIndex(resultSet, "xyz") == 3
      SqlUtils.getColumnIndex(resultSet, "not-found") == -1
   }

   def "test private constructor to complete coverage"() {

      expect:
      new SqlUtils() != null
   }
}
