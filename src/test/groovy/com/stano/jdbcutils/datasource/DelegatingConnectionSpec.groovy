package com.stano.jdbcutils.datasource

import org.joor.Reflect
import spock.lang.Specification

import java.sql.Connection
import java.sql.Savepoint
import java.util.concurrent.Executor

class DelegatingConnectionSpec extends Specification {

   def "ownsConnection should be false by default"() {

      def connection = Mock(Connection)
      def delegatingConnection = new DelegatingConnection(connection)

      expect:
      Reflect.on(delegatingConnection).get('connection') == connection
      Reflect.on(delegatingConnection).get('ownsConnection') == false
   }

   def "should be able to create a DelegatingConnection with Connection and ownsConnection values"() {

      def connection = Mock(Connection)
      def delegatingConnection = new DelegatingConnection(connection, ownsConnection)

      expect:
      Reflect.on(delegatingConnection).get('connection') == connection
      Reflect.on(delegatingConnection).get('ownsConnection') == expectedOwnsConnection

      where:
      ownsConnection | expectedOwnsConnection
      false          | false
      true           | true
   }

   def "all functions should delegate to the connection when ownsConnection is true"() {

      def columnIndexes = [1, 2] as int[]
      def columnNames = ['a', 'b'] as String[]
      def savepoint = Mock(Savepoint)
      def typeMap = [:] as Map
      def clientInfoProperties = Mock(Properties)
      def elements = [1, 2] as Object[]
      def executor = Mock(Executor)
      def connection = Mock(Connection)
      def delegatingConnection = new DelegatingConnection(connection, true)

      when:
      delegatingConnection.createStatement()
      delegatingConnection.createStatement(1, 2)
      delegatingConnection.createStatement(1, 2, 3)
      delegatingConnection.prepareStatement('sql')
      delegatingConnection.prepareStatement('sql', columnIndexes)
      delegatingConnection.prepareStatement('sql', columnNames)
      delegatingConnection.prepareStatement('sql', 1)
      delegatingConnection.prepareStatement('sql', 1, 2)
      delegatingConnection.prepareStatement('sql', 1, 2, 3)
      delegatingConnection.prepareCall('sql')
      delegatingConnection.prepareCall('sql', 1, 2)
      delegatingConnection.prepareCall('sql', 1, 2, 3)
      delegatingConnection.nativeSQL('sql')
      delegatingConnection.setAutoCommit(true)
      delegatingConnection.getAutoCommit()
      delegatingConnection.commit()
      delegatingConnection.rollback()
      delegatingConnection.rollback(savepoint)
      delegatingConnection.close()
      delegatingConnection.isClosed()
      delegatingConnection.getMetaData()
      delegatingConnection.setReadOnly(true)
      delegatingConnection.isReadOnly()
      delegatingConnection.setCatalog('catalog')
      delegatingConnection.getCatalog()
      delegatingConnection.setTransactionIsolation(1)
      delegatingConnection.getTransactionIsolation()
      delegatingConnection.getWarnings()
      delegatingConnection.clearWarnings()
      delegatingConnection.getTypeMap()
      delegatingConnection.setTypeMap(typeMap)
      delegatingConnection.setHoldability(1)
      delegatingConnection.getHoldability()
      delegatingConnection.setSavepoint()
      delegatingConnection.setSavepoint('savepoint')
      delegatingConnection.releaseSavepoint(savepoint)
      delegatingConnection.createClob()
      delegatingConnection.createBlob()
      delegatingConnection.createNClob()
      delegatingConnection.createSQLXML()
      delegatingConnection.isValid(100)
      delegatingConnection.setClientInfo('name', 'value')
      delegatingConnection.setClientInfo(clientInfoProperties)
      delegatingConnection.getClientInfo()
      delegatingConnection.getClientInfo('name')
      delegatingConnection.createArrayOf('typename', elements)
      delegatingConnection.createStruct('typename', elements)
      delegatingConnection.setSchema('schema')
      delegatingConnection.getSchema()
      delegatingConnection.abort(executor)
      delegatingConnection.setNetworkTimeout(executor, 100)
      delegatingConnection.getNetworkTimeout()
      delegatingConnection.unwrap(String.class)
      delegatingConnection.isWrapperFor(String.class)

      then:
      1 * connection.createStatement()
      1 * connection.createStatement(1, 2)
      1 * connection.createStatement(1, 2, 3)
      1 * connection.prepareStatement('sql')
      1 * connection.prepareStatement('sql', columnIndexes)
      1 * connection.prepareStatement('sql', columnNames)
      1 * connection.prepareStatement('sql', 1)
      1 * connection.prepareStatement('sql', 1, 2)
      1 * connection.prepareStatement('sql', 1, 2, 3)
      1 * connection.prepareCall('sql')
      1 * connection.prepareCall('sql', 1, 2)
      1 * connection.prepareCall('sql', 1, 2, 3)
      1 * connection.nativeSQL('sql')
      1 * connection.setAutoCommit(true)
      1 * connection.getAutoCommit()
      1 * connection.commit()
      1 * connection.rollback()
      1 * connection.rollback(savepoint)
      1 * connection.isClosed() >> false
      1 * connection.close()
      1 * connection.isClosed()
      1 * connection.getMetaData()
      1 * connection.setReadOnly(true)
      1 * connection.isReadOnly()
      1 * connection.setCatalog('catalog')
      1 * connection.getCatalog()
      1 * connection.setTransactionIsolation(1)
      1 * connection.getTransactionIsolation()
      1 * connection.getWarnings()
      1 * connection.clearWarnings()
      1 * connection.getTypeMap()
      1 * connection.setTypeMap(typeMap)
      1 * connection.setHoldability(1)
      1 * connection.getHoldability()
      1 * connection.setSavepoint()
      1 * connection.setSavepoint('savepoint')
      1 * connection.releaseSavepoint(savepoint)
      1 * connection.createClob()
      1 * connection.createBlob()
      1 * connection.createNClob()
      1 * connection.createSQLXML()
      1 * connection.isValid(100)
      1 * connection.setClientInfo('name', 'value')
      1 * connection.setClientInfo(clientInfoProperties)
      1 * connection.getClientInfo()
      1 * connection.getClientInfo('name')
      1 * connection.createArrayOf('typename', elements)
      1 * connection.createStruct('typename', elements)
      1 * connection.setSchema('schema')
      1 * connection.getSchema()
      1 * connection.abort(executor)
      1 * connection.setNetworkTimeout(executor, 100)
      1 * connection.getNetworkTimeout()
      1 * connection.unwrap(String.class)
      1 * connection.isWrapperFor(String.class)
   }

   def "only non-state changing functions should delegate to the connection when ownsConnection is false"() {

      def columnIndexes = [1, 2] as int[]
      def columnNames = ['a', 'b'] as String[]
      def savepoint = Mock(Savepoint)
      def typeMap = [:] as Map
      def clientInfoProperties = Mock(Properties)
      def elements = [1, 2] as Object[]
      def executor = Mock(Executor)
      def connection = Mock(Connection)
      def delegatingConnection = new DelegatingConnection(connection, false)

      when:
      delegatingConnection.createStatement()
      delegatingConnection.createStatement(1, 2)
      delegatingConnection.createStatement(1, 2, 3)
      delegatingConnection.prepareStatement('sql')
      delegatingConnection.prepareStatement('sql', columnIndexes)
      delegatingConnection.prepareStatement('sql', columnNames)
      delegatingConnection.prepareStatement('sql', 1)
      delegatingConnection.prepareStatement('sql', 1, 2)
      delegatingConnection.prepareStatement('sql', 1, 2, 3)
      delegatingConnection.prepareCall('sql')
      delegatingConnection.prepareCall('sql', 1, 2)
      delegatingConnection.prepareCall('sql', 1, 2, 3)
      delegatingConnection.nativeSQL('sql')
      delegatingConnection.setAutoCommit(true)
      delegatingConnection.getAutoCommit()
      delegatingConnection.commit()
      delegatingConnection.rollback()
      delegatingConnection.rollback(savepoint)
      delegatingConnection.close()
      delegatingConnection.isClosed()
      delegatingConnection.getMetaData()
      delegatingConnection.setReadOnly(true)
      delegatingConnection.isReadOnly()
      delegatingConnection.setCatalog('catalog')
      delegatingConnection.getCatalog()
      delegatingConnection.setTransactionIsolation(1)
      delegatingConnection.getTransactionIsolation()
      delegatingConnection.getWarnings()
      delegatingConnection.clearWarnings()
      delegatingConnection.getTypeMap()
      delegatingConnection.setTypeMap(typeMap)
      delegatingConnection.setHoldability(1)
      delegatingConnection.getHoldability()
      delegatingConnection.setSavepoint()
      delegatingConnection.setSavepoint('savepoint')
      delegatingConnection.releaseSavepoint(savepoint)
      delegatingConnection.createClob()
      delegatingConnection.createBlob()
      delegatingConnection.createNClob()
      delegatingConnection.createSQLXML()
      delegatingConnection.isValid(100)
      delegatingConnection.setClientInfo('name', 'value')
      delegatingConnection.setClientInfo(clientInfoProperties)
      delegatingConnection.getClientInfo()
      delegatingConnection.getClientInfo('name')
      delegatingConnection.createArrayOf('typename', elements)
      delegatingConnection.createStruct('typename', elements)
      delegatingConnection.setSchema('schema')
      delegatingConnection.getSchema()
      delegatingConnection.abort(executor)
      delegatingConnection.setNetworkTimeout(executor, 100)
      delegatingConnection.getNetworkTimeout()
      delegatingConnection.unwrap(String.class)
      delegatingConnection.isWrapperFor(String.class)

      then:
      1 * connection.createStatement()
      1 * connection.createStatement(1, 2)
      1 * connection.createStatement(1, 2, 3)
      1 * connection.prepareStatement('sql')
      1 * connection.prepareStatement('sql', columnIndexes)
      1 * connection.prepareStatement('sql', columnNames)
      1 * connection.prepareStatement('sql', 1)
      1 * connection.prepareStatement('sql', 1, 2)
      1 * connection.prepareStatement('sql', 1, 2, 3)
      1 * connection.prepareCall('sql')
      1 * connection.prepareCall('sql', 1, 2)
      1 * connection.prepareCall('sql', 1, 2, 3)
      1 * connection.nativeSQL('sql')
      0 * connection.setAutoCommit(true)
      1 * connection.getAutoCommit()
      0 * connection.commit()
      0 * connection.rollback()
      1 * connection.rollback(savepoint)
      0 * connection.isClosed()
      0 * connection.close()
      1 * connection.isClosed()
      1 * connection.getMetaData()
      0 * connection.setReadOnly(true)
      1 * connection.isReadOnly()
      0 * connection.setCatalog('catalog')
      1 * connection.getCatalog()
      0 * connection.setTransactionIsolation(1)
      1 * connection.getTransactionIsolation()
      1 * connection.getWarnings()
      1 * connection.clearWarnings()
      1 * connection.getTypeMap()
      1 * connection.setTypeMap(typeMap)
      0 * connection.setHoldability(1)
      1 * connection.getHoldability()
      1 * connection.setSavepoint()
      1 * connection.setSavepoint('savepoint')
      1 * connection.releaseSavepoint(savepoint)
      1 * connection.createClob()
      1 * connection.createBlob()
      1 * connection.createNClob()
      1 * connection.createSQLXML()
      1 * connection.isValid(100)
      0 * connection.setClientInfo('name', 'value')
      0 * connection.setClientInfo(clientInfoProperties)
      1 * connection.getClientInfo()
      1 * connection.getClientInfo('name')
      1 * connection.createArrayOf('typename', elements)
      1 * connection.createStruct('typename', elements)
      0 * connection.setSchema('schema')
      1 * connection.getSchema()
      0 * connection.abort(executor)
      0 * connection.setNetworkTimeout(executor, 100)
      1 * connection.getNetworkTimeout()
      1 * connection.unwrap(String.class)
      1 * connection.isWrapperFor(String.class)
   }
}
