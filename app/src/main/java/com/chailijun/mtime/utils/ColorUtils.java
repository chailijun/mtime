package com.chailijun.mtime.utils;

public class ColorUtils {

    /**
     * 将16进制字符串转换成16进制数
     *
     * @param colorStr
     * @return
     */
    public static int getTitleColor(String colorStr) {
        String titleColor = JsonIsNull.strIsNull(colorStr, "#666666");
        titleColor = titleColor.replace("#", "");
        return Integer.parseInt(titleColor, 16) + 0xff000000;
    }
}
