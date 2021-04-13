package com.magician.jdbc.helper.templete.model;

import com.magician.jdbc.core.constant.enums.OperType;

/**
 * 根据主键 增删改的 传参实体
 */
public class MagicianUpdate {

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

    /**
     * 操作类型
     * @return
     */
    private OperType operType;

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

    public OperType getOperType() {
        return operType;
    }

    public void setOperType(OperType operType) {
        this.operType = operType;
    }
}
