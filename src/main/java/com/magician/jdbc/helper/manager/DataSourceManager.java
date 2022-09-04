package com.magician.jdbc.helper.manager;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.*;

/**
 * Data source management
 */
public class DataSourceManager {

    private static Map<String, DataSource> dataSourceMap = new HashMap<>();

    /**
     * Default data source name
     */
    private static String defaultDataSourceName;

    public static Map<String, DataSource> getDataSourceMap() {
        return dataSourceMap;
    }

    public static void addDataSource(String name, DataSource dataSource) {
        dataSourceMap.put(name, dataSource);
    }
    /**
     * Get the default data source name
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
     * Get all datasource objects
     *
     * @return
     * @throws Exception
     */
    public static Map<String, DataSource> getDruidDataSources() throws Exception {
        return dataSourceMap;
    }

    /**
     * get database connection
     *
     * @return
     * @throws Exception
     */
    public static Connection getConnection(String dataSourceName) throws Exception {
        DataSource dataSource = dataSourceMap.get(dataSourceName);
        if (dataSource == null) {
            throw new Exception("No data source with name [" + dataSourceName + "] found");
        }
        Connection connection = dataSource.getConnection();
        connection.setAutoCommit(true);
        return connection;
    }
}
