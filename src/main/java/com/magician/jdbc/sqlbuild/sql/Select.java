package com.magician.jdbc.sqlbuild.sql;

import com.magician.jdbc.sqlbuild.BaseSqlBuilder;

import java.lang.reflect.Field;

/**
 * 查询
 */
public class Select extends BaseSqlBuilder {

    /**
     * 要查询的字段
     */
    private Class setFields;

    public Select(String tableName){
        this.setTableName(tableName);
    }

    /**
     * 设置要查询的字段
     * @param cls
     * @return
     */
    public Select column(Class cls){
        this.setFields = cls;
        return this;
    }

    /**
     * 构建sql
     * @return
     * @throws Exception
     */
    @Override
    public String builder() throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append("select ");
        sql.append(getColumn());
        sql.append(" from ");
        sql.append(this.getTableName());
        sql.append(this.getWhere());
        return sql.toString();
    }

    /**
     * 获取要查询的字段
     * @return
     */
    private String getColumn(){
        if(this.setFields == null){
            return "*";
        }

        Field[] fields = this.setFields.getDeclaredFields();
        if(fields == null || fields.length < 1){
            return "*";
        }

        return getCol(fields).toString();
    }
}
