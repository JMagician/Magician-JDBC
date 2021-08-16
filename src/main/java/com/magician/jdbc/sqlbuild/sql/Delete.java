package com.magician.jdbc.sqlbuild.sql;

import com.magician.jdbc.sqlbuild.BaseSqlBuilder;

/**
 * 删除
 */
public class Delete extends BaseSqlBuilder {

    public Delete(String tableName){
        this.setTableName(tableName);
    }

    /**
     * 构建sql
     * @return
     * @throws Exception
     */
    @Override
    public String builder() throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append("delete from ");
        sql.append(this.getTableName());
        sql.append(this.getWhere());
        return sql.toString();
    }

    @Override
    public BaseSqlBuilder end(String end) {
        return this;
    }
}
