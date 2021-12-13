package com.magician.jdbc.helper.templete.conversion;

import com.magician.jdbc.core.util.JSONUtil;
import com.magician.jdbc.helper.templete.model.Condition;
import com.magician.jdbc.helper.templete.model.SqlBuilderModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SqlConversion {

    /**
     * 往sql后面拼接条件
     * @param sql
     * @param conditions
     * @return
     */
    public static SqlBuilderModel getSql(StringBuffer sql, List<Condition> conditions){
        SqlBuilderModel sqlBuilderModel = new SqlBuilderModel();
        List<Object> params = new ArrayList<>();
        for (Condition condition : conditions) {
            if (condition.getVal() == null) {
                continue;
            }

            sql.append(condition.getKey());

            if (!condition.getVal().equals(Condition.NOT_WHERE)) {
                params.add(condition.getVal());
            }
        }

        sqlBuilderModel.setSql(sql.toString());
        sqlBuilderModel.setParams(params.toArray());
        return sqlBuilderModel;
    }

    /**
     * 构建sql语句，处理占位符
     *
     * @param sql
     * @param args
     * @return
     * @throws Exception
     */
    public static SqlBuilderModel builderSql(String sql, Object args) throws Exception {

        SqlBuilderModel sqlBuilderModel = new SqlBuilderModel();

        if (args == null) {
            sqlBuilderModel.setSql(sql);
            sqlBuilderModel.setParams(new Object[0]);
            return sqlBuilderModel;
        }

        Map<String, Object> jsonObject = JSONUtil.toMap(args);

        List<Object> params = new ArrayList<>();

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
        Pattern pattern = Pattern.compile("(\\{((?!}).)*\\})");
        Matcher matcher = pattern.matcher(sql);
        while (matcher.find()) {
            String matcherName = matcher.group();
            sql = sql.replace(matcherName,"?");
            String filedName = matcherName.replace("{","").replace("}","");
            params.add(jsonObject.get(filedName));
        }
        return sql;
    }
}
