package com.chailijun.mtime.utils;


public class JsonIsNull {

    /**对字符串进行非空判断
     * @param value
     * @return
     */
    public static String strIsNull(String value) {

        return value == null||value.equals("") ? "" : value;
    }

    public static String strIsNull(String value,String defaultStr) {

        return value == null||value.equals("") ? defaultStr : value;
    }
}
