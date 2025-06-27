package com.stano.jdbcutils.utils;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public final class SqlGenerator {
  public static String delete(String table, String... params) {
    String paramNames = Arrays.stream(params)
                              .map(column -> String.format("%s=:%s", column, column))
                              .collect(Collectors.joining(" and "));

    return String.format("delete from %s where %s", table, paramNames);
  }

  public static String insert(String table, String... columns) {
    String columnNames = String.join(",", columns);
    String paramNames = Arrays.stream(columns).map(column -> ":" + column).collect(Collectors.joining(","));

    return String.format("insert into %s (%s) values (%s)", table, columnNames, paramNames);
  }

  public static String update(String table, Set<String> columns, Set<String> params) {
    String columnNames = columns.stream()
                                .map(column -> String.format("%s=:%s", column, column))
                                .collect(Collectors.joining(","));
    String paramNames = params.stream()
                              .map(column -> String.format("%s=:%s", column, column))
                              .collect(Collectors.joining(" and "));

    return String.format("update %s set %s where %s", table, columnNames, paramNames);
  }

  private SqlGenerator() {
  }
}
