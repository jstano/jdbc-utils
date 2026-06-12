package com.stano.jdbcutils.utils;

import java.sql.Types;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public enum SqlType {
    NULL(Types.NULL),
    OBJECT(Types.OTHER, Types.JAVA_OBJECT),
    STRING(
            Types.CHAR,
            Types.VARCHAR,
            Types.LONGVARCHAR,
            Types.NCHAR,
            Types.NVARCHAR,
            Types.LONGNVARCHAR),
    BYTE(Types.TINYINT),
    SHORT(Types.SMALLINT),
    INTEGER(Types.INTEGER),
    LONG(Types.BIGINT),
    FLOAT(Types.REAL),
    DOUBLE(Types.DOUBLE, Types.FLOAT),
    DECIMAL(Types.DECIMAL, Types.NUMERIC),
    BOOLEAN(Types.BIT, Types.BOOLEAN),
    DATE(Types.DATE),
    TIME(Types.TIME),
    DATETIME(Types.TIMESTAMP),
    CLOB(Types.CLOB),
    BINARY(Types.BINARY, Types.VARBINARY, Types.LONGVARBINARY, Types.BLOB);

    public static SqlType fromSqlType(int sqlType) {
        return Arrays.stream(values())
                .filter(it -> it.sqlTypes.contains(sqlType))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    private final Set<Integer> sqlTypes;

    SqlType(Integer... sqlTypes) {
        this.sqlTypes = new HashSet<>(Arrays.asList(sqlTypes));
    }
}
