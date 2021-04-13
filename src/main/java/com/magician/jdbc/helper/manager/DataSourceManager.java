package com.magician.jdbc.helper.manager;

import com.magician.jdbc.MagicianJDBCConfig;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.*;

/**
 * 数据源管理
 */
public class DataSourceManager {

    /**
     * 获取默认数据源名称
     *
     * @return
     */
    public static String getDefaultDataSourceName() {
        return MagicianJDBCConfig.getDefaultDataSourceName();
    }

    /**
     * 获取所有的数据源对象
     *
     * @return
     * @throws Exception
     */
    public static Map<String, DataSource> getDruidDataSources() throws Exception {
        return MagicianJDBCConfig.getDataSourceMap();
    }

    /**
     * 获取数据库连接
     *
     * @return
     * @throws Exception
     */
    public static Connection getConnection(String dataSourceName) throws Exception {
        DataSource dataSource = MagicianJDBCConfig.getDataSourceMap().get(dataSourceName);
        if (dataSource == null) {
            throw new Exception("没有找到name为[" + dataSourceName + "]的数据源");
        }
        Connection connection = dataSource.getConnection();
        connection.setAutoCommit(true);
        return connection;
    }
}
