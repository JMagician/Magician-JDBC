package com.magician.jdbc.helper.templete.base;

import com.magician.jdbc.helper.manager.ConnectionManager;
import com.magician.jdbc.helper.templete.model.SqlBuilderModel;
import com.magician.jdbc.helper.manager.DataSourceManager;
import com.magician.jdbc.core.util.JSONUtil;
import com.magician.jdbc.core.util.ThreadUtil;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * jdbc模板父类
 */
public class BaseJdbcTemplate {

    /**
     * 获取数据库连接
     *
     * @return
     * @throws Exception
     */
    public static ConnectionManager getConnection(String dataSourceName) throws Exception {
        ConnectionManager connectionManager = new ConnectionManager();

        /* 获取当前线程中的Connection */
        Object obj = ThreadUtil.getThreadLocal().get();

        /* 获取数据源名称 */
        String dataSourceName2 = getDataSourceName(dataSourceName);

        if (obj != null) {
            Map<String, Connection> connections = (Map<String, Connection>) obj;
            connectionManager.setConnection(connections.get(dataSourceName2));
            connectionManager.setHasTraction(false);
        } else {
            connectionManager.setConnection(DataSourceManager.getConnection(dataSourceName2));
            connectionManager.setHasTraction(true);
        }
        return connectionManager;
    }

    /**
     * 获取数据源名称
     *
     * @return str
     */
    public static String getDataSourceName(String dataSourceName) {
        if (dataSourceName == null) {
            dataSourceName = DataSourceManager.getDefaultDataSourceName();
        }
        return dataSourceName;
    }

    /**
     * 构建sql语句
     *
     * @param sql
     * @param args
     * @return
     * @throws Exception
     */
    public static SqlBuilderModel builderSql(String sql, Object args) throws Exception {

        SqlBuilderModel sqlBuilderModel = new SqlBuilderModel();

        Map<String, Object> jsonObject = JSONUtil.toMap(args);

        List<Object> params = new ArrayList<>();

        sql = replaceMatcher(sql,params,jsonObject);
        sql = formatMatcher(sql,params,jsonObject);

        sqlBuilderModel.setSql(sql);
        sqlBuilderModel.setParams(params.toArray());

        return sqlBuilderModel;
    }

    /**
     * 替换占位符为问号
     * @param sql
     * @param params
     * @param jsonObject
     * @return
     */
    private static String formatMatcher(String sql,List<Object> params, Map<String, Object> jsonObject){
        Pattern pattern = Pattern.compile("(#\\{((?!}).)*\\})");
        Matcher matcher = pattern.matcher(sql);
        while (matcher.find()) {
            String matcherName = matcher.group();
            sql = sql.replace(matcherName,"?");
            String filedName = getFiledName(matcherName,"#");
            params.add(jsonObject.get(filedName));
        }
        return sql;
    }

    /**
     * 替换占位符为具体的值
     * @param sql
     * @param params
     * @param jsonObject
     * @return
     */
    private static String replaceMatcher(String sql,List<Object> params,Map<String, Object> jsonObject){
        Pattern pattern = Pattern.compile("(\\$\\{((?!}).)*\\})");
        Matcher matcher = pattern.matcher(sql);
        while (matcher.find()) {
            String matcherName = matcher.group();
            String filedName = getFiledName(matcherName,"$");
            sql = sql.replace(matcherName,String.valueOf(jsonObject.get(filedName)));
        }
        return sql;
    }

    /**
     * 获取参数中的name
     * @param matcherStr
     * @param startStr
     * @return
     */
    private static String getFiledName(String matcherStr,String startStr){
        return matcherStr.replace(startStr+"{","").replace("}","");
    }
}

