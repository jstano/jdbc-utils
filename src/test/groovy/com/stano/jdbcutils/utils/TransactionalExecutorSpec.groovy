package com.stano.jdbcutils.utils


import spock.lang.Specification

import java.sql.Connection
import java.sql.SQLException

class TransactionalExecutorSpec extends Specification {

   def "execute(SQLExecutor) should commit if no exceptions are thrown"() {

      def connection = Mock(Connection) {
         getAutoCommit() >> true
      }

      def transactionalExecutor = TransactionalExecutor.withConnection(connection)

      when:
      transactionalExecutor.execute(new SQLExecutor() {

         @Override
         void execute() throws SQLException {

         }
      })

      then:
      1 * connection.setAutoCommit(false)

      then:
      1 * connection.commit()

      then:
      1 * connection.setAutoCommit(true)
   }

   def "if a SQLException is thrown in execute(SQLExecutor) in the getAutoCommit call then a RuntimeSQLException should be thrown"() {

      def connection = Mock(Connection) {
         getAutoCommit() >> {
            throw new SQLException('getAutoCommit failed')
         }
      }

      def transactionalExecutor = TransactionalExecutor.withConnection(connection)

      when:
      transactionalExecutor.execute(new SQLExecutor() {

         @Override
         void execute() throws SQLException {

            throw new SQLException('SQL WENT SOUTH')
         }
      })

      then:
      0 * connection.setAutoCommit(false)
      0 * connection.commit()
      0 * connection.rollback()
      0 * connection.setAutoCommit(true)

      then:
      def exception = thrown(RuntimeSQLException)
      exception.cause instanceof SQLException
      exception.cause.message == 'getAutoCommit failed'
   }

   def "if a Throwable is thrown in execute(SQLExecutor) in the executor then we should rollback and a RuntimeSQLException should be thrown"() {

      def connection = Mock(Connection) {
         getAutoCommit() >> true
      }

      def transactionalExecutor = TransactionalExecutor.withConnection(connection)

      when:
      transactionalExecutor.execute(new SQLExecutor() {

         @Override
         void execute() throws SQLException {

            throw new OutOfMemoryError('Out of memory')
         }
      })

      then:
      1 * connection.setAutoCommit(false)

      then:
      1 * connection.rollback()

      then:
      1 * connection.setAutoCommit(true)

      then:
      def exception = thrown(RuntimeSQLException)
      exception.cause instanceof OutOfMemoryError
      exception.cause.message == 'Out of memory'
   }

   def "execute(SQLExecutorWithResult) should commit and return the result if no exceptions are thrown"() {

      def connection = Mock(Connection) {
         getAutoCommit() >> true
      }

      def transactionalExecutor = TransactionalExecutor.withConnection(connection)

      when:
      def result = transactionalExecutor.execute(new SQLExecutorWithResult<String>() {

         @Override
         String execute() throws SQLException {
            return 'abc123'
         }
      })

      then:
      1 * connection.setAutoCommit(false)

      then:
      1 * connection.commit()

      then:
      1 * connection.setAutoCommit(true)

      then:
      result == 'abc123'
   }

   def "if a SQLException is thrown in execute(SQLExecutorWithResult) in the getAutoCommit call then a RuntimeSQLException should be thrown"() {

      def connection = Mock(Connection) {
         getAutoCommit() >> {
            throw new SQLException('getAutoCommit failed')
         }
      }

      def transactionalExecutor = TransactionalExecutor.withConnection(connection)

      when:
      transactionalExecutor.execute(new SQLExecutorWithResult<String>() {

         @Override
         String execute() throws SQLException {

            throw new SQLException('SQL WENT SOUTH')
         }
      })

      then:
      0 * connection.setAutoCommit(false)
      0 * connection.commit()
      0 * connection.rollback()
      0 * connection.setAutoCommit(true)

      then:
      def exception = thrown(RuntimeSQLException)
      exception.cause instanceof SQLException
      exception.cause.message == 'getAutoCommit failed'
   }

   def "if a Throwable is thrown in execute(SQLExecutorWithResult) in the executor then we should rollback and a RuntimeSQLException should be thrown"() {

      def connection = Mock(Connection) {
         getAutoCommit() >> true
      }

      def transactionalExecutor = TransactionalExecutor.withConnection(connection)

      when:
      transactionalExecutor.execute(new SQLExecutorWithResult<String>() {

         @Override
         String execute() throws SQLException {

            throw new OutOfMemoryError('Out of memory')
         }
      })

      then:
      1 * connection.setAutoCommit(false)

      then:
      1 * connection.rollback()

      then:
      1 * connection.setAutoCommit(true)

      then:
      def exception = thrown(RuntimeSQLException)
      exception.cause instanceof OutOfMemoryError
      exception.cause.message == 'Out of memory'
   }
}
