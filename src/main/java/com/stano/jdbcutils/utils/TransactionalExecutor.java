package com.stano.jdbcutils.utils;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionalExecutor {
    private final Connection connection;

    public static TransactionalExecutor withConnection(Connection connection) {
        return new TransactionalExecutor(connection);
    }

    public <R> R execute(SQLExecutorWithResult<R> executor) {
        try {
            boolean autoCommit = connection.getAutoCommit();

            try {
                connection.setAutoCommit(false);

                R result = executor.execute();

                connection.commit();

                return result;
            } catch (Throwable x) {
                connection.rollback();

                throw new RuntimeSQLException(x);
            } finally {
                connection.setAutoCommit(autoCommit);
            }
        } catch (SQLException x) {
            throw new RuntimeSQLException(x);
        }
    }

    public void execute(SQLExecutor executor) {
        try {
            boolean autoCommit = connection.getAutoCommit();

            try {
                connection.setAutoCommit(false);

                executor.execute();

                connection.commit();
            } catch (Throwable x) {
                connection.rollback();

                throw new RuntimeSQLException(x);
            } finally {
                connection.setAutoCommit(autoCommit);
            }
        } catch (SQLException x) {
            throw new RuntimeSQLException(x);
        }
    }

    private TransactionalExecutor(Connection connection) {
        this.connection = connection;
    }
}
