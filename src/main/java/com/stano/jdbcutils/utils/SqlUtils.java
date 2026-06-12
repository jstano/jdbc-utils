package com.stano.jdbcutils.utils;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class SqlUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(SqlUtils.class);

    public static String escapeSqlString(String sql) {
        return sql.replaceAll("'", "''");
    }

    public static String quoteSqlString(String sql) {
        return "'" + escapeSqlString(sql) + "'";
    }

    public static SqlType getColumnType(ResultSet resultSet, String columnName) {
        return getColumnType(resultSet, getColumnIndex(resultSet, columnName));
    }

    public static SqlType getColumnType(ResultSet resultSet, int columnIndex) {
        try {
            return SqlType.fromSqlType(resultSet.getMetaData().getColumnType(columnIndex));
        } catch (SQLException x) {
            throw new RuntimeSQLException(x);
        }
    }

    public static int getColumnIndex(ResultSet resultSet, String columnName) {
        try {
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

            for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
                if (resultSetMetaData.getColumnName(i).equalsIgnoreCase(columnName)) {
                    return i;
                }
            }

            LOGGER.debug("Unable to locate column {} in ResultSet", columnName);

            return -1;
        } catch (SQLException x) {
            throw new RuntimeSQLException(x);
        }
    }

    private SqlUtils() {}
}
