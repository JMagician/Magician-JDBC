package com.magician.jdbc.sqlbuild;

import com.magician.jdbc.sqlbuild.sql.Delete;
import com.magician.jdbc.sqlbuild.sql.Insert;
import com.magician.jdbc.sqlbuild.sql.Select;
import com.magician.jdbc.sqlbuild.sql.Update;

/**
 * 构建sql
 */
public class SqlBuilder {

    /**
     * 获取插入sql构造器
     * @param tableName
     * @return
     */
    public static Insert insert(String tableName){
        return new Insert(tableName);
    }

    /**
     * 获取修改sql构造器
     * @param tableName
     * @return
     */
    public static Update update(String tableName){
        return new Update(tableName);
    }

    /**
     * 获取查询sql构造器
     * @param tableName
     * @return
     */
    public static Select select(String tableName){
        return new Select(tableName);
    }

    /**
     * 获取删除sql构造器
     * @param tableName
     * @return
     */
    public static Delete delete(String tableName){
        return new Delete(tableName);
    }
}
