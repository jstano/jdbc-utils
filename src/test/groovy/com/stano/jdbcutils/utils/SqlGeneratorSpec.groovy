package com.stano.jdbcutils.utils

import spock.lang.Specification

class SqlGeneratorSpec extends Specification {

  def "should be able to generate a delete statement"() {

    def sql = SqlGenerator.delete("test", "a", "b", "c")

    expect:
    sql == "delete from test where a=:a and b=:b and c=:c"
  }

  def "should be able to generate an insert statement"() {

    def sql = SqlGenerator.insert("test", "a", "b", "c")

    expect:
    sql == "insert into test (a,b,c) values (:a,:b,:c)"
  }

  def "should be able to generate an update statement"() {

    def sql = SqlGenerator.update("test", ["a", "b", "c"] as Set, ["x", "y", "z"] as Set)

    expect:
    sql == "update test set a=:a,b=:b,c=:c where x=:x and y=:y and z=:z"
  }

  def "call the private constructor so the coverage is accurate"() {

    expect:
    new SqlGenerator()
  }
}
