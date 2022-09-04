package com.magician.jdbc.core.util;

/**
 * String utility class
 */
public class StringUtil {

    /**
     * Determine if it is empty
     * @param str
     * @return
     */
    public static boolean isNull(String str){
        if(str == null || str.equals("")){
            return true;
        }
        return false;
    }
}
