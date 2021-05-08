package com.magician.jdbc.sqlbuild;

import com.magician.jdbc.sqlbuild.sql.Delete;
import com.magician.jdbc.sqlbuild.sql.Insert;
import com.magician.jdbc.sqlbuild.sql.Select;
import com.magician.jdbc.sqlbuild.sql.Update;

/**
 * 构建sql
 */
public class SqlBuilder {

    public static Insert insert(String tableName){
        return new Insert(tableName);
    }

    public static Update update(String tableName){
        return new Update(tableName);
    }

    public static Select select(String tableName){
        return new Select(tableName);
    }

    public static Delete delete(String tableName){
        return new Delete(tableName);
    }
}
