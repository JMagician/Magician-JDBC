package com.magician.jdbc.sqlbuild;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.magician.jdbc.core.util.StringUtil;

import java.lang.reflect.Field;

/**
 * 实体类
 */
public abstract class BaseSqlBuilder {

    /**
     * 表名
     */
    private String tableName;

    /**
     * 条件
     */
    private String where;

    public BaseSqlBuilder byPrimaryKey(String primaryKeyName){
        StringBuffer where = new StringBuffer();
        where.append(" where ");
        where.append(primaryKeyName);
        where.append("=#{");
        where.append(primaryKeyName);
        where.append("}");
        this.where = where.toString();

        return this;
    }

    public BaseSqlBuilder where(String whereStr){
        StringBuffer where = new StringBuffer();
        where.append(" where ");
        where.append(whereStr);
        this.where = where.toString();

        return this;
    }

    public String getFieldName(Field field){
        JsonProperty jsonProperty = field.getAnnotation(JsonProperty.class);
        if(jsonProperty == null || StringUtil.isNull(jsonProperty.value())){
            return field.getName();
        } else {
            return jsonProperty.value();
        }
    }

    public abstract String builder() throws Exception;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getWhere() {
        return where;
    }

    public void setWhere(String where) {
        this.where = where;
    }
}
