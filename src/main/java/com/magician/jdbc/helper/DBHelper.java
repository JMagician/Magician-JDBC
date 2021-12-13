package com.magician.jdbc.helper;

import com.magician.jdbc.core.util.JSONUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.*;

/**
 * JDBC连接帮助类
 */
public class DBHelper {

    private static Logger logger = LoggerFactory.getLogger(DBHelper.class);

    /**
     * 查询列表
     *
     * @param sql        sql语句
     * @param connection 数据库连接
     * @param params     参数
     * @return 结果集
     * @throws Exception
     */
    public static List<Map<String, Object>> selectList(String sql, Connection connection, Object[] params) throws Exception {
        if (params == null) {
            params = new Object[0];
        }
        ResultSet resultSet = select(sql, connection, params);
        List<Map<String, Object>> list = new ArrayList<>();

        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        int count = resultSetMetaData.getColumnCount();

        while (resultSet.next()) {
            Map<String, Object> rows = new HashMap<>();
            for (int i = 1; i <= count; i++) {
                String key = resultSetMetaData.getColumnLabel(i);
                Object value = resultSet.getObject(i);
                rows.put(key, value);
            }
            list.add(rows);
        }
        return list;
    }

    /**
     * 有条件查询
     *
     * @param sql        sql语句
     * @param connection 数据库连接
     * @param params     参数
     * @return 结果集
     * @throws Exception
     */
    public static ResultSet select(String sql, Connection connection, Object[] params) throws Exception {
        if(logger.isDebugEnabled()){
            logger.debug("sql:{},params:{}",sql, JSONUtil.toJSONString(params));
        }
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        if(params != null && params.length > 0){
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }
        }
        return preparedStatement.executeQuery();
    }

    /**
     * 有条件增删改
     *
     * @param sql        sql语句
     * @param connection 数据库连接
     * @param params     参数
     * @return 受影响行数
     * @throws Exception
     */
    public static int update(String sql, Connection connection, Object[] params) throws Exception {
        if (params == null) {
            params = new Object[0];
        }
        if(logger.isDebugEnabled()){
            logger.debug("sql:{},params:{}",sql, JSONUtil.toJSONString(params));
        }
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        if(params != null && params.length > 0){
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }
        }
        return preparedStatement.executeUpdate();
    }
}
