package com.magician.jdbc.helper.templete.util;

import com.magician.jdbc.helper.templete.model.Condition;

import java.util.ArrayList;
import java.util.List;

/**
 * 条件构造器
 */
public class ConditionBuilder {

    /**
     * 条件集合
     */
    private List<Condition> conditionList;

    /**
     * 创建一个构造器
     * @return
     */
    public static ConditionBuilder createCondition(){
        ConditionBuilder conditionBuilder = new ConditionBuilder();
        conditionBuilder.conditionList = new ArrayList<>();
        return conditionBuilder;
    }

    /**
     * 添加条件
     * @param key
     * @param val
     * @return
     */
    public ConditionBuilder add(String key, Object... val){
        conditionList.add(Condition.get(key, val));
        return this;
    }

    /**
     * 获取条件集合
     * @return
     */
    public List<Condition> build(){
        return conditionList;
    }
}
