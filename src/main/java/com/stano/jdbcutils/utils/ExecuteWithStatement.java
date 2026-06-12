package com.stano.jdbcutils.utils;

import java.sql.Statement;

public interface ExecuteWithStatement<T> {
    T executeWithStatement(Statement statement);
}
