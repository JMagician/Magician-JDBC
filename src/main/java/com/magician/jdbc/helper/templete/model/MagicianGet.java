package com.magician.jdbc.helper.templete.model;

/**
 * 根据主键查询一条数据的 传参实体
 */
public class MagicianGet {

    /**
     * 表名
     * @return
     */
    private String tableName;

    /**
     * 主键名称
     * @return
     */
    private String primaryKey;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
    }
}
