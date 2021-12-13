package com.magician.jdbc.helper.templete.model;

/**
 * 条件构造器
 */
public class Condition {

    private String key;

    private Object val;

    public static final String NOT_WHERE = "notWhere";

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getVal() {
        return val;
    }

    public void setVal(Object val) {
        this.val = val;
    }

    /**
     * 获取一个条件构造器
     * @param key
     * @param val
     * @return
     */
    public static Condition get(String key, Object val){
        Condition condition = new Condition();
        condition.setKey(key);
        condition.setVal(val);
        return condition;
    }
}
