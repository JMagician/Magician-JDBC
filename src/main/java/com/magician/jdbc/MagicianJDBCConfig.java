package com.magician.jdbc;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * JDBC配置
 */
public class MagicianJDBCConfig {

    private static Map<String, DataSource> dataSourceMap = new HashMap<>();

    /**
     * 默认数据源名称
     */
    private static String defaultDataSourceName;

    public static Map<String, DataSource> getDataSourceMap() {
        return dataSourceMap;
    }

    public static void addDataSource(String name, DataSource dataSource) {
        MagicianJDBCConfig.dataSourceMap.put(name, dataSource);
    }

    public static String getDefaultDataSourceName() {
        return defaultDataSourceName;
    }

    public static void setDefaultDataSourceName(String defaultDataSourceName) {
        MagicianJDBCConfig.defaultDataSourceName = defaultDataSourceName;
    }
}
