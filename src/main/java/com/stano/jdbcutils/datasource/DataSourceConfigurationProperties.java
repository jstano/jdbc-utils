package com.stano.jdbcutils.datasource;

import org.apache.commons.lang3.StringUtils;

public class DataSourceConfigurationProperties {
  private String url;
  private String username;
  private String password;
  private String driver;
  private boolean showSql;
  private boolean autoMigrate;

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getDriver() {
    return driver;
  }

  public void setDriver(String driver) {
    this.driver = driver;
  }

  public boolean isShowSql() {
    return showSql;
  }

  public void setShowSql(boolean showSql) {
    this.showSql = showSql;
  }

  public boolean isAutoMigrate() {
    return autoMigrate;
  }

  public void setAutoMigrate(boolean autoMigrate) {
    this.autoMigrate = autoMigrate;
  }

  public boolean isValid() {
    return StringUtils.isNotBlank(url);
  }

  public DataSourceProperties toDataSourceProperties() {
    if (!isValid()) {
      throw new IllegalArgumentException("The url is null");
    }

    return new DataSourceProperties(url,
                                    username,
                                    password,
                                    driver == null ? null : DriverType.valueOf(driver.toUpperCase()));
  }
}
