package com.stano.jdbcutils.utils


import spock.lang.Specification

import java.sql.Connection
import java.sql.Statement

public class StatementWrapperSpec
   extends Specification {

   def "ExecuteWithStatement"() {

      def statement = Mock(Statement)

      def connection = Mock(Connection)
      connection.createStatement() >> statement

      def statementWrapper = new StatementWrapper(connection)

      when:
      def result = statementWrapper.executeWithStatement(new ExecuteWithStatement() {

         @Override
         Object executeWithStatement(Statement st) {
            return "abc"
         }
      })

      then:
      1 * statement.close()

      then:
      result == "abc"
   }
}
