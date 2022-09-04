package com.magician.jdbc.helper.templete.conversion;

import com.magician.jdbc.core.util.JSONUtil;
import com.magician.jdbc.helper.templete.model.Condition;
import com.magician.jdbc.helper.templete.model.SqlBuilderModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * sql converter
 */
public class SqlConversion {

    /**
     * Splicing conditions behind sql
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
            sql.append(" ");
            sql.append(condition.getKey());

            if (isNotWhere(condition.getVal())) {
                continue;
            }
            for(Object arg : condition.getVal()){
                params.add(arg);
            }
        }

        sqlBuilderModel.setSql(sql.toString());
        sqlBuilderModel.setParams(params.toArray());
        return sqlBuilderModel;
    }

    /**
     * is it where
     * @param val
     * @return
     */
    private static boolean isNotWhere(Object[] val){
        if(val.length == 1 && val[0].equals(Condition.NOT_WHERE)) {
            return true;
        }
        return false;
    }

    /**
     * Build sql statements, handle placeholders
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
     * Replace placeholders with question marks
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
