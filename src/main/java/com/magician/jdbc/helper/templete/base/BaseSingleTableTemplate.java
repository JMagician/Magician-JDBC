package com.magician.jdbc.helper.templete.base;

import com.magician.jdbc.core.util.JSONUtil;
import com.magician.jdbc.helper.templete.JdbcTemplate;
import com.magician.jdbc.helper.templete.model.MagicianGet;
import com.magician.jdbc.helper.templete.model.MagicianUpdate;

import java.util.Map;

/**
 * 单表操作
 */
public class BaseSingleTableTemplate {

    /**
     * 根据主键查询一条数据
     *
     * @param magicianGet        注解
     * @param dataSourceName 数据源
     * @param param          参数
     * @param resultType         返回类型
     * @return 数据
     * @throws Exception 异常
     */
    public static <T> T get(MagicianGet magicianGet, String dataSourceName, Object param, Class<T> resultType) throws Exception {

        StringBuffer sql = new StringBuffer();
        sql.append("select * from ");
        sql.append(magicianGet.getTableName());
        sql.append(" where ");
        sql.append(magicianGet.getPrimaryKey());
        sql.append(" = ?");

        return JdbcTemplate.create(dataSourceName).selectOne(sql.toString(), new Object[]{param}, resultType);
    }

    /**
     * 单表增删改
     *
     * @param magicianUpdate     注解
     * @param dataSourceName 数据源
     * @param param          参数
     * @return 数据
     * @throws Exception 异常
     */
    public static int update(MagicianUpdate magicianUpdate, String dataSourceName, Object param) throws Exception {
        switch (magicianUpdate.getOperType()) {
            case DELETE:
                return doDelete(magicianUpdate, dataSourceName, param);
            case UPDATE:
                return doUpdate(magicianUpdate, dataSourceName, param);
            case INSERT:
                return doInsert(magicianUpdate, dataSourceName, param);
        }
        return 0;
    }

    /**
     * 单表增删改
     *
     * @param magicianUpdate     注解
     * @param dataSourceName 数据源
     * @param param          参数
     * @return 数据
     * @throws Exception 异常
     */
    private static int doDelete(MagicianUpdate magicianUpdate, String dataSourceName, Object param) throws Exception {

        StringBuffer sql = new StringBuffer();
        sql.append("delete from ");
        sql.append(magicianUpdate.getTableName());

        sql.append(" where ");
        sql.append(magicianUpdate.getPrimaryKey());
        sql.append(" = ?");
        param = new Object[]{param};

        return JdbcTemplate.create(dataSourceName).update(sql.toString(), param);
    }

    /**
     * 单表增删改
     *
     * @param magicianUpdate     注解
     * @param dataSourceName 数据源
     * @param param          参数
     * @return 数据
     * @throws Exception 异常
     */
    private static int doInsert(MagicianUpdate magicianUpdate, String dataSourceName, Object param) throws Exception {
        Map<String, Object> jsonObject = JSONUtil.toMap(param);

        StringBuffer sql = new StringBuffer();
        sql.append("insert into ");
        sql.append(magicianUpdate.getTableName());
        sql.append("(");
        boolean isFirst = true;
        for (String key : jsonObject.keySet()) {
            Object val = jsonObject.get(key);
            if (val != null) {
                if (!isFirst) {
                    sql.append(",");
                }
                sql.append(key);
                isFirst = false;
            }
        }
        sql.append(") values(");
        isFirst = true;
        for (String key : jsonObject.keySet()) {
            Object val = jsonObject.get(key);
            if (val != null) {
                if (!isFirst) {
                    sql.append(",");
                }
                sql.append("#{");
                sql.append(key);
                sql.append("}");
                isFirst = false;
            }
        }
        sql.append(")");

        return JdbcTemplate.create(dataSourceName).update(sql.toString(), param);
    }

    /**
     * 单表增删改
     *
     * @param magicianUpdate     注解
     * @param dataSourceName 数据源
     * @param param          参数
     * @return 数据
     * @throws Exception 异常
     */
    private static int doUpdate(MagicianUpdate magicianUpdate, String dataSourceName, Object param) throws Exception {
        Map<String, Object> jsonObject = JSONUtil.toMap(param);

        StringBuffer sql = new StringBuffer();
        sql.append("update ");
        sql.append(magicianUpdate.getTableName());
        sql.append(" set ");
        boolean isFirst = true;
        for (String key : jsonObject.keySet()) {
            Object val = jsonObject.get(key);
            if (val != null && !key.equals(magicianUpdate.getPrimaryKey())) {
                if (!isFirst) {
                    sql.append(",");
                }
                sql.append(key);
                sql.append(" = #{");
                sql.append(key);
                sql.append("}");
                isFirst = false;
            }
        }

        sql.append(" where ");
        sql.append(magicianUpdate.getPrimaryKey());
        sql.append(" = #{");
        sql.append(magicianUpdate.getPrimaryKey());
        sql.append("}");

        return JdbcTemplate.create(dataSourceName).update(sql.toString(), param);
    }
}
