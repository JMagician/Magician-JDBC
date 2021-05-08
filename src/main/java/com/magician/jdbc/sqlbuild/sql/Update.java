package com.magician.jdbc.sqlbuild.sql;

import com.magician.jdbc.sqlbuild.BaseSqlBuilder;

import java.lang.reflect.Field;

/**
 * 修改
 */
public class Update extends BaseSqlBuilder {

    /**
     * 要修改的字段
     */
    private Class setFields;

    public Update(String tableName){
        this.setTableName(tableName);
    }

    /**
     * 设置要修改的字段
     * @param cls
     * @return
     */
    public Update column(Class cls){
        this.setFields = cls;
        return this;
    }

    /**
     * 构建sql
     * @return
     * @throws Exception
     */
    public String builder() throws Exception {
        Field[] fields = this.setFields.getDeclaredFields();
        if(fields == null || fields.length < 1){
            throw new Exception(setFields.getName()+"里面没有字段，不符合规则");
        }

        StringBuffer sql = new StringBuffer();
        sql.append("update ");
        sql.append(this.getTableName());
        sql.append(" set ");
        boolean isFirst = true;
        for(Field field : fields){
            if(ignore(field)){
                continue;
            }

            String fieldName = getFieldName(field);

            if(!isFirst){
                sql.append(",");
            }
            sql.append(fieldName);
            sql.append("=#{");
            sql.append(fieldName);
            sql.append("} ");

            isFirst = false;
        }
        sql.append(this.getWhere());
        return sql.toString();
    }
}
