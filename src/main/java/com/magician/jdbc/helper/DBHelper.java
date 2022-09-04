package com.magician.jdbc.helper;

import com.magician.jdbc.core.util.JSONUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.*;

/**
 * JDBC connection helper class
 */
public class DBHelper {

    private static Logger logger = LoggerFactory.getLogger(DBHelper.class);

    /**
     * Query list
     *
     * @param sql
     * @param connection
     * @param params
     * @return
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
     * Conditional query
     *
     * @param sql
     * @param connection
     * @param params
     * @return
     * @throws Exception
     */
    public static ResultSet select(String sql, Connection connection, Object[] params) throws Exception {
        if (logger.isDebugEnabled()) {
            logger.debug("sql:{},params:{}", sql, JSONUtil.toJSONString(params));
        }
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        if (params != null && params.length > 0) {
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }
        }
        return preparedStatement.executeQuery();
    }

    /**
     * Conditional additions and deletions
     *
     * @param sql
     * @param connection
     * @param params
     * @return
     * @throws Exception
     */
    public static int update(String sql, Connection connection, Object[] params) throws Exception {
        if (params == null) {
            params = new Object[0];
        }
        if (logger.isDebugEnabled()) {
            logger.debug("sql:{},params:{}", sql, JSONUtil.toJSONString(params));
        }
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        if (params != null && params.length > 0) {
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }
        }
        return preparedStatement.executeUpdate();
    }
}
