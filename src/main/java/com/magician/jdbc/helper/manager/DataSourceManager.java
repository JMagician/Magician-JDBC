package com.magician.jdbc.helper.manager;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.*;

/**
 * 数据源管理
 */
public class DataSourceManager {

    private static Map<String, DataSource> dataSourceMap = new HashMap<>();

    /**
     * 默认数据源名称
     */
    private static String defaultDataSourceName;

    public static Map<String, DataSource> getDataSourceMap() {
        return dataSourceMap;
    }

    public static void addDataSource(String name, DataSource dataSource) {
        dataSourceMap.put(name, dataSource);
    }
    /**
     * 获取默认数据源名称
     *
     * @return
     */
    public static String getDefaultDataSourceName() {
        return defaultDataSourceName;
    }

    public static void setDefaultDataSourceName(String defaultDataSourceName) {
        DataSourceManager.defaultDataSourceName = defaultDataSourceName;
    }


    /**
     * 获取所有的数据源对象
     *
     * @return
     * @throws Exception
     */
    public static Map<String, DataSource> getDruidDataSources() throws Exception {
        return dataSourceMap;
    }

    /**
     * 获取数据库连接
     *
     * @return
     * @throws Exception
     */
    public static Connection getConnection(String dataSourceName) throws Exception {
        DataSource dataSource = dataSourceMap.get(dataSourceName);
        if (dataSource == null) {
            throw new Exception("没有找到name为[" + dataSourceName + "]的数据源");
        }
        Connection connection = dataSource.getConnection();
        connection.setAutoCommit(true);
        return connection;
    }
}
