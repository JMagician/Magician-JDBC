package com.magician.jdbc.helper.templete.util;

import com.magician.jdbc.helper.templete.model.Condition;

import java.util.ArrayList;
import java.util.List;

/**
 * Condition builder
 */
public class ConditionBuilder {

    /**
     * condition set
     */
    private List<Condition> conditionList;

    /**
     * create a conditionBuilder object
     * @return
     */
    public static ConditionBuilder createCondition(){
        ConditionBuilder conditionBuilder = new ConditionBuilder();
        conditionBuilder.conditionList = new ArrayList<>();
        return conditionBuilder;
    }

    /**
     * Add condition
     * @param key
     * @param val
     * @return
     */
    public ConditionBuilder add(String key, Object... val){
        conditionList.add(Condition.get(key, val));
        return this;
    }

    /**
     * Get condition set
     * @return
     */
    public List<Condition> build(){
        return conditionList;
    }
}
