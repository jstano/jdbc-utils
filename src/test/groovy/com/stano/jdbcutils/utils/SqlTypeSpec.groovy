package com.stano.jdbcutils.utils

import spock.lang.Specification

import java.sql.Types

class SqlTypeSpec extends Specification {

   def "the types should map to the correct sql types"() {

      expect:
      SqlType.fromSqlType(Types.NULL) == SqlType.NULL
      SqlType.fromSqlType(Types.OTHER) == SqlType.OBJECT
      SqlType.fromSqlType(Types.JAVA_OBJECT) == SqlType.OBJECT
      SqlType.fromSqlType(Types.CHAR) == SqlType.STRING
      SqlType.fromSqlType(Types.VARCHAR) == SqlType.STRING
      SqlType.fromSqlType(Types.LONGVARCHAR) == SqlType.STRING
      SqlType.fromSqlType(Types.NCHAR) == SqlType.STRING
      SqlType.fromSqlType(Types.NVARCHAR) == SqlType.STRING
      SqlType.fromSqlType(Types.LONGNVARCHAR) == SqlType.STRING
      SqlType.fromSqlType(Types.TINYINT) == SqlType.BYTE
      SqlType.fromSqlType(Types.SMALLINT) == SqlType.SHORT
      SqlType.fromSqlType(Types.INTEGER) == SqlType.INTEGER
      SqlType.fromSqlType(Types.BIGINT) == SqlType.LONG
      SqlType.fromSqlType(Types.REAL) == SqlType.FLOAT
      SqlType.fromSqlType(Types.DOUBLE) == SqlType.DOUBLE
      SqlType.fromSqlType(Types.FLOAT) == SqlType.DOUBLE
      SqlType.fromSqlType(Types.DECIMAL) == SqlType.DECIMAL
      SqlType.fromSqlType(Types.NUMERIC) == SqlType.DECIMAL
      SqlType.fromSqlType(Types.BIT) == SqlType.BOOLEAN
      SqlType.fromSqlType(Types.BOOLEAN) == SqlType.BOOLEAN
      SqlType.fromSqlType(Types.DATE) == SqlType.DATE
      SqlType.fromSqlType(Types.TIME) == SqlType.TIME
      SqlType.fromSqlType(Types.TIMESTAMP) == SqlType.DATETIME
      SqlType.fromSqlType(Types.CLOB) == SqlType.CLOB
      SqlType.fromSqlType(Types.BINARY) == SqlType.BINARY
      SqlType.fromSqlType(Types.VARBINARY) == SqlType.BINARY
      SqlType.fromSqlType(Types.LONGVARBINARY) == SqlType.BINARY
      SqlType.fromSqlType(Types.BLOB) == SqlType.BINARY
   }

   def "should get an IllegalArgumentException if an unknown type is passed in to fromSqlType"() {

      when:
      SqlType.fromSqlType(9876)

      then:
      thrown IllegalArgumentException
   }
}
