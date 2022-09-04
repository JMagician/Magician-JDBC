package com.magician.jdbc.core.constant.enums;

/**
 * Transaction isolation level
 */
public enum TractionLevel {

    READ_COMMITTED(2,"READ_COMMITTED"),
    READ_UNCOMMITTED(1,"READ_UNCOMMITTED"),
    REPEATABLE_READ(4,"REPEATABLE_READ"),
    SERIALIZABLE(8,"SERIALIZABLE");

    private int level;
    private String code;

    TractionLevel(int level, String code){
        this.level = level;
        this.code = code;
    }

    public int getLevel() {
        return level;
    }

    public String getCode() {
        return code;
    }
}
