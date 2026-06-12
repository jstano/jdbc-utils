package com.stano.jdbcutils.utils;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface RowConsumer {
    void consume(ResultSet rs) throws SQLException;
}
