package com.magician.jdbc.helper.templete.model;

/**
 * After the sql is built, it is returned in this format
 */
public class SqlBuilderModel {

    private String sql;

    private Object[] params;

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public Object[] getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }
}
