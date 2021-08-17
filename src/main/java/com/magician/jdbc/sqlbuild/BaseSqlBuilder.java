package com.magician.jdbc.sqlbuild;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private StringBuffer where = new StringBuffer(" where");

    /**
     * 以主键作为判断条件
     * @param primaryKeyName
     * @return
     */
    public BaseSqlBuilder byPrimaryKey(String primaryKeyName){
        where = new StringBuffer();
        where.append(" where ");
        where.append(primaryKeyName);
        where.append("=#{");
        where.append(primaryKeyName);
        where.append("}");

        return this;
    }

    /**
     * 自定义条件判断
     * @param whereStr
     * @return
     */
    public BaseSqlBuilder where(String whereStr){
        where.append(" ");
        where.append(whereStr);
        return this;
    }

    /**
     * 构建sql
     * @return
     * @throws Exception
     */
    public abstract String builder() throws Exception;

    /**
     * 其他sql，比如order by，group by，limit等 都可以写在里面，支持混写
     * @param end
     * @return
     */
    public abstract BaseSqlBuilder end(String end);

    /**
     * 获取字段名称
     * @param field
     * @return
     */
    protected String getFieldName(Field field){
        JsonProperty jsonProperty = field.getAnnotation(JsonProperty.class);
        if(jsonProperty == null || StringUtil.isNull(jsonProperty.value())){
            return field.getName();
        } else {
            return jsonProperty.value();
        }
    }

    /**
     * 判断是否排除排除该字段
     * @param field
     * @return
     */
    protected boolean ignore(Field field){
        JsonIgnore jsonIgnore = field.getAnnotation(JsonIgnore.class);
        if(jsonIgnore != null){
            return true;
        }
        return false;
    }

    /**
     * 获取查询和修改的字段
     * @param fields
     * @return
     */
    protected StringBuffer getCol(Field[] fields){
        StringBuffer sql = new StringBuffer();
        boolean isFirst = true;
        for (Field field : fields) {
            if(ignore(field)){
                continue;
            }

            String fieldName = getFieldName(field);

            if (!isFirst) {
                sql.append(",");
            }
            sql.append(fieldName);
            isFirst = false;
        }

        return sql;
    }

    protected String getTableName() {
        return tableName;
    }

    protected void setTableName(String tableName) {
        this.tableName = tableName;
    }

    protected String getWhere() {
        if(where == null){
            return "";
        }
        String whereStr = where.toString();
        if(whereStr == null || whereStr.endsWith("where")){
            return "";
        }
        return whereStr;
    }

    public void setWhere(StringBuffer where) {
        this.where = where;
    }
}
