package com.stano.jdbcutils.datasource.impl

import com.stano.check.CheckFailureException
import spock.lang.Specification

class DataSourceUrlParserSpec extends Specification {
  def "should get an CheckFailureException or an IllegalArgumentException if the url is null or blank or is malformed"() {
    when:
    DataSourceUrlParser.parseDataSourceUrl(url)

    then:
    thrown expectedException

    where:
    url       | expectedException
    null      | CheckFailureException
    ""        | CheckFailureException
    "  "      | CheckFailureException
    "abc:123" | IllegalArgumentException
  }

  def "should be able to parse a valid url and get out the various parts"() {
    def props = DataSourceUrlParser.parseDataSourceUrl(url)

    expect:
    props.server == server
    props.database == database
    props.options == options

    where:
    url                                                                  | server         | database        | options
    '//server/database'                                                  | 'server'       | 'database'      | ''
    '//server:port/database'                                             | 'server:port'  | 'database'      | ''
    '//server/database;ssl=true;option=2'                                | 'server'       | 'database'      | ';ssl=true;option=2'
    '//server:port/database;ssl=true;option=2'                           | 'server:port'  | 'database'      | ';ssl=true;option=2'
    '//server/database;ssl=true'                                         | 'server'       | 'database'      | ';ssl=true'
    '//1.2.3.4/database;ssl=true'                                        | '1.2.3.4'      | 'database'      | ';ssl=true'
    '//1.2.3.4:1234/database;ssl=true'                                   | '1.2.3.4:1234' | 'database'      | ';ssl=true'
    '//server-name/database-name;ssl=true'                               | 'server-name'  | 'database-name' | ';ssl=true'
    '//server_name/database_name;ssl=true'                               | 'server_name'  | 'database_name' | ';ssl=true'
    '//server-name/database-name?ssl=true&sslmode=allow'                 | 'server-name'  | 'database-name' | '?ssl=true&sslmode=allow'
    'jdbc:postgresql://server/database'                                  | 'server'       | 'database'      | ''
    'jdbc:postgresql://server:1234/database'                             | 'server:1234'  | 'database'      | ''
    'jdbc:postgresql://server/database;ssl=true'                         | 'server'       | 'database'      | ';ssl=true'
    'jdbc:postgresql://server:1234/database;ssl=true'                    | 'server:1234'  | 'database'      | ';ssl=true'
    'jdbc:sqlserver://server;databaseName=database'                      | 'server'       | 'database'      | ''
    'jdbc:sqlserver://server:1234;databaseName=database'                 | 'server:1234'  | 'database'      | ''
    'jdbc:sqlserver://server;databaseName=database;ssl=true'             | 'server'       | 'database'      | ';ssl=true'
    'jdbc:sqlserver://server:1234;databaseName=database;ssl=true'        | 'server:1234'  | 'database'      | ';ssl=true'
    'jdbc:sqlserver://1.2.3.4;databaseName=database;ssl=true'            | '1.2.3.4'      | 'database'      | ';ssl=true'
    'jdbc:sqlserver://1.2.3.4:1234;databaseName=database;ssl=true'       | '1.2.3.4:1234' | 'database'      | ';ssl=true'
    'jdbc:sqlserver://server-name;databaseName=database-name;ssl=true'   | 'server-name'  | 'database-name' | ';ssl=true'
    'jdbc:sqlserver://server_name;databaseName=database_name;ssl=true'   | 'server_name'  | 'database_name' | ';ssl=true'
    'jdbc:postgresql://server-name/database-name?ssl=true&sslmode=allow' | 'server-name'  | 'database-name' | '?ssl=true&sslmode=allow'
    'mem:database-name'                                                  | 'mem'          | 'database-name' | ''
    'mem:database-name;DB_CLOSE_DELAY=-1'                                | 'mem'          | 'database-name' | ';DB_CLOSE_DELAY=-1'
    'jdbc:h2:mem:database-name'                                          | 'mem'          | 'database-name' | ''
    'jdbc:h2:mem:database-name;DB_CLOSE_DELAY=-1'                        | 'mem'          | 'database-name' | ';DB_CLOSE_DELAY=-1'
    'jdbc:hsqldb:mem:database-name'                                      | 'mem'          | 'database-name' | ''
  }

  def "call the private constructor so the coverage is accurate"() {
    expect:
    new DataSourceUrlParser()
  }
}
