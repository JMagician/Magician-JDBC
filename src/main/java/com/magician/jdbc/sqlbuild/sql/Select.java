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
    /**
     * 其他sql，比如order by，group by，limit等 都可以写在里面，支持混写
     */
    private String end;

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
        sql.append(this.getEnd());
        return sql.toString();
    }

    /**
     * 其他sql，比如order by，group by，limit等 都可以写在里面，支持混写
     * @param end
     * @return
     */
    @Override
    public BaseSqlBuilder end(String end){
        this.end = " " + end;
        return this;
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

    private String getEnd() {
        if(end == null || "null".equals(end)){
            return "";
        }
        return end;
    }
}
