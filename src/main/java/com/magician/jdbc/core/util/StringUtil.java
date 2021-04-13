package com.magician.jdbc.core.util;

public class StringUtil {

    public static boolean isNull(String str){
        if(str == null || str.equals("")){
            return true;
        }
        return false;
    }
}
