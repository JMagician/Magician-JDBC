package com.magician.jdbc;

import javax.sql.DataSource;

/**
 * JDBC创建
 */
public class MagicianJDBC {

    /**
     * 创建一个JDBC实例
     * @return
     */
    public static MagicianJDBC createJDBC(){
        return new MagicianJDBC();
    }

    /**
     * 添加数据源
     * @param name
     * @param dataSource
     * @return
     */
    public MagicianJDBC addDataSource(String name, DataSource dataSource){
        MagicianJDBCConfig.addDataSource(name, dataSource);
        return this;
    }

    /**
     * 设置默认数据源名称
     * @return
     */
    public MagicianJDBC defaultDataSourceName(String name){
        MagicianJDBCConfig.setDefaultDataSourceName(name);
        return this;
    }
}
