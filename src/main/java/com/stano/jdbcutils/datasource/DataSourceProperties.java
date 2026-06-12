package com.stano.jdbcutils.datasource;

import com.stano.jdbcutils.datasource.impl.DataSourceUrlParser;
import java.util.Objects;

public class DataSourceProperties implements java.io.Serializable {
    private final String url;
    private final String username;
    private final String password;
    private final DriverType driverType;

    public DataSourceProperties(String url) {
        this.url = url;
        this.username = null;
        this.password = null;
        this.driverType = DriverType.fromConnectionUrl(url);
    }

    public DataSourceProperties(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.driverType = DriverType.fromConnectionUrl(url);
    }

    public DataSourceProperties(
            String url, String username, String password, DriverType driverType) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.driverType = driverType == null ? DriverType.fromConnectionUrl(url) : driverType;
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public DriverType getDriverType() {
        return driverType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DataSourceProperties that = (DataSourceProperties) o;
        return Objects.equals(url, that.url)
                && Objects.equals(username, that.username)
                && Objects.equals(password, that.password)
                && driverType == that.driverType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, username, password, driverType);
    }

    public String getServer() {
        return DataSourceUrlParser.parseDataSourceUrl(url).getServer();
    }

    public String getDatabase() {
        return DataSourceUrlParser.parseDataSourceUrl(url).getDatabase();
    }
}
