package com.stano.jdbcutils.datasource.impl;

import com.stano.check.Check;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class DataSourceUrlParser {
    public static DataSourceUrlProperties parseDataSourceUrl(String url) {
        Check.notBlank(url);

        url = url.trim();

        if (url.startsWith("jdbc:sqlserver:")) {
            Matcher matcher =
                    Pattern.compile(
                                    "(?<prefix>jdbc:sqlserver:)?\\/\\/(?<server>[a-zA-Z_0-9\\-\\.\\:]+)(?<database>;databaseName=[a-zA-Z_0-9\\-\\.]+);?(?<options>.*)")
                            .matcher(url);

            if (matcher.find()) {
                String server = matcher.group("server");
                String database = matcher.group("database");
                String options = matcher.group("options");

                return new DataSourceUrlProperties(
                        server,
                        database.replace(";databaseName=", ""),
                        options.isEmpty() ? "" : ";" + options);
            }
        } else if (url.startsWith("mem:")
                || url.startsWith("jdbc:h2:mem:")
                || url.startsWith("jdbc:hsqldb:mem:")) {
            Matcher matcher =
                    Pattern.compile(
                                    "(?<server>mem)\\:(?<database>[a-zA-Z_0-9\\-\\.]+)(?<optionSeparator>[;\\?])?(?<options>.*)")
                            .matcher(url);

            if (matcher.find()) {
                String server = matcher.group("server");
                String database = matcher.group("database");
                String optionSeparator = matcher.group("optionSeparator");
                String options = matcher.group("options");

                return new DataSourceUrlProperties(
                        server, database, options.isEmpty() ? "" : optionSeparator + options);
            }
        } else if (url.startsWith("jdbc:xmla:")) {
            Matcher matcher =
                    Pattern.compile(
                                    "(?<prefix>jdbc:xmla:)?(?<server>Server=[a-zA-Z_0-9\\-\\.\\:\\/]+)(?<catalog>;Catalog=[a-zA-Z_0-9\\-\\.]+);?(?<options>.*)")
                            .matcher(url);

            if (matcher.find()) {
                String server =
                        matcher.group("server")
                                .replace("Server=", "")
                                .replace("http://", "")
                                .replace("/olap/msmdpump.dll", "");
                String database = matcher.group("catalog").replace(";Catalog=", "");
                String options = matcher.group("options");

                return new DataSourceUrlProperties(
                        server,
                        database.replace(";databaseName=", ""),
                        options.isEmpty() ? "" : ";" + options);
            }
        } else {
            Matcher matcher =
                    Pattern.compile(
                                    "(?<prefix>jdbc:.+:)?\\/\\/(?<server>[a-zA-Z_0-9\\-\\.\\:]+)\\/(?<database>[a-zA-Z_0-9\\-\\.]+)(?<optionSeparator>[;\\?])?(?<options>.*)")
                            .matcher(url);

            if (matcher.find()) {
                String server = matcher.group("server");
                String database = matcher.group("database");
                String optionSeparator = matcher.group("optionSeparator");
                String options = matcher.group("options");

                return new DataSourceUrlProperties(
                        server, database, options.isEmpty() ? "" : optionSeparator + options);
            }
        }

        throw new IllegalArgumentException(String.format("The url '%s' is not valid", url));
    }

    private DataSourceUrlParser() {}
}
