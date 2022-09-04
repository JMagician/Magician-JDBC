package com.magician.jdbc;

import com.magician.jdbc.helper.manager.DataSourceManager;

import javax.sql.DataSource;

/**
 * JDBC management
 */
public class MagicianJDBC {

    /**
     * Create a JDBC instance
     * @return
     */
    public static MagicianJDBC createJDBC(){
        return new MagicianJDBC();
    }

    /**
     * Add datasource
     * @param name
     * @param dataSource
     * @return
     */
    public MagicianJDBC addDataSource(String name, DataSource dataSource){
        DataSourceManager.addDataSource(name, dataSource);
        return this;
    }

    /**
     * Set default datasource name
     * @return
     */
    public MagicianJDBC defaultDataSourceName(String name){
        DataSourceManager.setDefaultDataSourceName(name);
        return this;
    }
}
