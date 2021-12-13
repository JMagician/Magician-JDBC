package com.magician.jdbc.helper.templete;

import com.magician.jdbc.core.util.JSONUtil;
import com.magician.jdbc.core.util.ThreadUtil;
import com.magician.jdbc.helper.DBHelper;
import com.magician.jdbc.helper.manager.ConnectionManager;
import com.magician.jdbc.helper.manager.DataSourceManager;
import com.magician.jdbc.helper.templete.conversion.SqlConversion;
import com.magician.jdbc.helper.templete.model.Condition;
import com.magician.jdbc.helper.templete.model.PageModel;
import com.magician.jdbc.helper.templete.model.PageParamModel;
import com.magician.jdbc.helper.templete.model.SqlBuilderModel;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 数据库操作
 */
public class JDBCTemplate {

    /**
     * 数据源
     */
    private String dataSource;

    private JDBCTemplate(){}

    /**
     * 获取对象
     * @return
     */
    public static JDBCTemplate get(){
        return get(null);
    }

    /**
     * 获取对象
     * @param dataSource
     * @return
     */
    public static JDBCTemplate get(String dataSource){
        if (dataSource == null) {
            dataSource = DataSourceManager.getDefaultDataSourceName();
        }
        JDBCTemplate jdbcTemplate = new JDBCTemplate();
        jdbcTemplate.dataSource = dataSource;
        return jdbcTemplate;
    }

    /* -------------------------------------- 单表的无sql操作 ------------------------------------------ */

    /**
     * 无sql，单表查询
     * @param tableName
     * @param conditions
     * @param cls
     * @param <T>
     * @return
     * @throws Exception
     */
    public <T> List<T> select(String tableName, List<Condition> conditions, Class<T> cls) throws Exception {
        ConnectionManager connectionManager = getConnection();

        StringBuffer sql = new StringBuffer();
        sql.append("select * from ");
        sql.append(tableName);

        List<Map<String, Object>> result = null;
        if (conditions != null && conditions.size() > 0) {
            sql.append(" where ");
            SqlBuilderModel sqlBuilderModel = SqlConversion.getSql(sql, conditions);
            result = DBHelper.selectList(sqlBuilderModel.getSql(), connectionManager.getConnection(), sqlBuilderModel.getParams());
        } else {
            result = DBHelper.selectList(sql.toString(), connectionManager.getConnection(), null);
        }

        List<T> resultList = new ArrayList<>();
        for (Map<String, Object> item : result){
            resultList.add(JSONUtil.toJavaObject(item, cls));
        }
        connectionManager.close();
        return resultList;
    }

    /**
     * 无sql更新数据
     * @param tableName
     * @param data
     * @param conditions
     * @return
     * @throws Exception
     */
    public int update(String tableName,  Object data, List<Condition> conditions) throws Exception {
        if(conditions == null || conditions.size() < 1){
            throw new Exception("为了安全起见，不带条件的修改操作，请自己写sql处理");
        }

        ConnectionManager connectionManager = getConnection();

        Map<String, Object> paramMap = JSONUtil.toMap(data);

        StringBuffer sql = new StringBuffer();
        sql.append("update ");
        sql.append(tableName);
        sql.append(" set ");

        List<Object> paramList = new ArrayList<>();

        Boolean first = false;
        for (Map.Entry<String, Object> item : paramMap.entrySet()) {
            if(item.getValue() == null){
                continue;
            }
            if(first){
                sql.append(",");
            }
            sql.append(item.getKey());
            sql.append(" = ?");
            paramList.add(item.getValue());

            first = true;
        }
        sql.append(" where ");
        SqlBuilderModel sqlBuilderModel = SqlConversion.getSql(sql, conditions);

        int result = DBHelper.update(sqlBuilderModel.getSql(), connectionManager.getConnection(), sqlBuilderModel.getParams());
        connectionManager.close();
        return result;
    }

    /**
     * 无sql删除数据
     * @param tableName
     * @param conditions
     * @return
     * @throws Exception
     */
    public int delete(String tableName, List<Condition> conditions) throws Exception {
        if(conditions == null || conditions.size() < 1){
            throw new Exception("为了安全起见，不带条件的删除操作，请自己写sql处理");
        }
        ConnectionManager connectionManager = getConnection();

        StringBuffer sql = new StringBuffer();
        sql.append("delete from ");
        sql.append(tableName);
        sql.append(" where ");
        SqlBuilderModel sqlBuilderModel = SqlConversion.getSql(sql, conditions);

        int result = DBHelper.update(sqlBuilderModel.getSql(), connectionManager.getConnection(), sqlBuilderModel.getParams());
        connectionManager.close();
        return result;
    }

    /**
     * 无sql插入数据
     * @param tableName
     * @param data
     * @return
     * @throws Exception
     */
    public int insert(String tableName, Object data) throws Exception {
        ConnectionManager connectionManager = getConnection();

        StringBuffer sql = new StringBuffer();
        sql.append("insert into ");
        sql.append(tableName);
        sql.append(" (");

        StringBuffer values = new StringBuffer();
        values.append(") values (");

        Map<String, Object> paramMap = JSONUtil.toMap(data);

        List<Object> paramList = new ArrayList<>();

        Boolean first = false;
        for(Map.Entry<String, Object> item : paramMap.entrySet()){
            if(item.getValue() == null){
                continue;
            }
            if(first){
                sql.append(",");
                values.append(",");
            }
            sql.append(item.getKey());
            values.append("?");
            paramList.add(item.getValue());

            first = true;
        }

        sql.append(values);
        sql.append(")");

        int result = DBHelper.update(sql.toString(), connectionManager.getConnection(), paramList.toArray());
        connectionManager.close();
        return result;
    }

    /* -------------------------------------- 自定义sql做复杂操作 ------------------------------------------ */

    /**
     * 查询列表
     * @param sql
     * @param param
     * @param cls
     * @param <T>
     * @return
     * @throws Exception
     */
    public <T> List<T> selectList(String sql, Object param, Class<T> cls) throws Exception {

        ConnectionManager connectionManager = getConnection();

        List<T> resultList = new ArrayList<>();

        if (param instanceof Object[]) {
            List<Map<String, Object>> result = DBHelper.selectList(sql, connectionManager.getConnection(), (Object[])param);
            for (Map<String, Object> item : result){
                resultList.add(JSONUtil.toJavaObject(item, cls));
            }
            return resultList;
        }

        SqlBuilderModel sqlBuilderModel = SqlConversion.builderSql(sql, param);

        List<Map<String, Object>> result = DBHelper.selectList(sqlBuilderModel.getSql(), connectionManager.getConnection(), sqlBuilderModel.getParams());
        for (Map<String, Object> item : result){
            resultList.add(JSONUtil.toJavaObject(item, cls));
        }

        connectionManager.close();
        return resultList;
    }

    /**
     * 查询列表
     * @param sql
     * @param cls
     * @param <T>
     * @return
     * @throws Exception
     */
    public <T> List<T> selectList(String sql, Class<T> cls) throws Exception {
        return selectList(sql, new Object[0], cls);
    }

    /**
     * 查询一条数据
     * @param sql
     * @param cls
     * @param <T>
     * @return
     * @throws Exception
     */
    public <T> T selectOne(String sql, Object param, Class<T> cls) throws Exception {
        List<T> resultList = selectList(sql, param, cls);
        if (resultList != null && resultList.size() > 1) {
            throw new Exception("数据超过了一条");
        }
        if (resultList != null && resultList.size() < 1) {
            return null;
        }
        return resultList.get(0);
    }

    /**
     * 查询一条数据
     * @param sql
     * @param cls
     * @param <T>
     * @return
     * @throws Exception
     */
    public <T> T selectOne(String sql, Class<T> cls) throws Exception {
        return selectOne(sql, new Object[0], cls);
    }

    /**
     * 增删改
     * @param sql
     * @param param
     * @return
     */
    public int exec(String sql, Object param) throws Exception {
        ConnectionManager connectionManager = getConnection();
        if (param instanceof Object[]) {
            return DBHelper.update(sql, connectionManager.getConnection(), (Object[])param);
        }

        SqlBuilderModel sqlBuilderModel = SqlConversion.builderSql(sql, param);
        int result = DBHelper.update(sqlBuilderModel.getSql(), connectionManager.getConnection(), sqlBuilderModel.getParams());

        connectionManager.close();
        return result;
    }

    /**
     * 增删改
     * @param sql
     * @return
     */
    public int exec(String sql) throws Exception {
        return exec(sql, new Object[0]);
    }

    /* -------------------------------------- 分页查询 ------------------------------------------ */

    /**
     * 采用默认的countSql进行分页查询
     * @param sql
     * @param pageParamModel
     * @param cls
     * @param <T>
     * @return
     * @throws Exception
     */
    public <T> PageModel<T> selectPage(String sql, PageParamModel pageParamModel, Class<T> cls) throws Exception {
        String countSql = "select count(0) total from(" + sql + ") tbl";
        return selectPageCustomCountSql(sql, countSql, pageParamModel, cls);
    }

    /**
     * 采用自定义countSql 进行分页查询
     * @param sql
     * @param countSql
     * @param pageParamModel
     * @param cls
     * @param <T>
     * @return
     * @throws Exception
     */
    public <T> PageModel<T> selectPageCustomCountSql(String sql, String countSql, PageParamModel pageParamModel, Class<T> cls) throws Exception {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(sql);
        stringBuffer.append(" limit {pageStart},{pageSize}");

        Map result = selectOne(countSql, pageParamModel.getParam(), Map.class);
        Object totalObj = result.get("total");
        if (totalObj == null || "".equals(totalObj)) {
            totalObj = 0;
        }

        pageParamModel.getParam().put("pageStart",(pageParamModel.getCurrentPage()-1) * pageParamModel.getPageSize());
        pageParamModel.getParam().put("pageSize",pageParamModel.getPageSize());

        List<T> resultList = selectList(stringBuffer.toString(), pageParamModel.getParam(), cls);

        PageModel<T> pageModel = new PageModel<>();
        pageModel.setPageCount(Integer.parseInt(totalObj.toString()));
        pageModel.setCurrentPage(pageParamModel.getCurrentPage());
        pageModel.setPageSize(pageParamModel.getPageSize());
        pageModel.setPageTotal(getPageTotal(pageModel));
        pageModel.setDataList(resultList);
        return pageModel;
    }

    /**
     * 计算总页数
     * @param pageModel
     * @return
     */
    private int getPageTotal(PageModel pageModel){
        int pageTotal = pageModel.getPageCount() / pageModel.getPageSize();

        if (pageModel.getPageCount() % pageModel.getPageSize() == 0) {
            return pageTotal;
        } else {
            return pageTotal + 1;
        }
    }

    /**
     * 获取事务里的连接
     * @return
     */
    private ConnectionManager getConnection() throws Exception {
        ConnectionManager connectionManager = new ConnectionManager();

        Object obj = ThreadUtil.getThreadLocal().get();
        if (obj != null) {
            Map<String, Connection> connections = (Map<String, Connection>) obj;

            connectionManager.setTransaction(true);
            connectionManager.setConnection(connections.get(dataSource));
        } else {
            connectionManager.setTransaction(false);
            connectionManager.setConnection(DataSourceManager.getConnection(dataSource));
        }

        return connectionManager;
    }
}
