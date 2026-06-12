package com.stano.jdbcutils.sqlloader.impl;

import com.stano.jdbcutils.datasource.DriverType;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Optional;
import java.util.WeakHashMap;
import org.apache.commons.io.IOUtils;

public final class SqlLoaderCache {
    private static final Map<String, String> sqlCache = new WeakHashMap<>();

    public static String loadSql(String name, DriverType driverType, ClassLoader classLoader) {
        Optional<String> sql = getSqlFromCache(name, driverType);

        if (!sql.isPresent()) {
            sql = findSqlResource(name, driverType, classLoader);

            if (!sql.isPresent()) {
                throw new IllegalStateException(String.format("SQL file '%s' not found.", name));
            }

            putSqlInCache(name, driverType, sql.get());
        }

        return sql.get();
    }

    private static Optional<String> findSqlResource(
            String name, DriverType driverType, ClassLoader classLoader) {
        try {
            InputStream in =
                    classLoader.getResourceAsStream(
                            name + "-" + driverType.name().toLowerCase() + ".sql");

            if (in == null) {
                in = classLoader.getResourceAsStream(name + ".sql");
            }

            if (in != null) {
                return Optional.of(IOUtils.toString(in).trim());
            }

            return Optional.empty();
        } catch (IOException x) {
            throw new IllegalStateException(x);
        }
    }

    private static Optional<String> getSqlFromCache(String name, DriverType driverType) {
        return Optional.ofNullable(sqlCache.get(name + "-" + driverType.name().toLowerCase()));
    }

    private static void putSqlInCache(String name, DriverType driverType, String sql) {
        sqlCache.put(name + "-" + driverType.name().toLowerCase(), sql);
    }

    private SqlLoaderCache() {}
}
