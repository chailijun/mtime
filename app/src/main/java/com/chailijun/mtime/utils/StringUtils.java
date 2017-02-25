package com.chailijun.mtime.utils;

public class StringUtils {


    /**
     * 去换行、两端空格(全角、半角)
     *
     * @param string
     * @return
     */
    public static String myTrim(String string) {

        String replace = string.replace("\n", "").trim();

        // 删除前部所有的全角空格
        while (replace.charAt(0) == '　') {
            replace = replace.substring(1);
        }

        // 删除后部所有的全角空格
        while (replace.charAt(replace.length() - 1) == '　') {
            replace = replace.substring(0, replace.length() - 1);
        }

        return replace;
    }

}
