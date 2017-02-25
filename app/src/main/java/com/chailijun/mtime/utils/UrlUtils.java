package com.chailijun.mtime.utils;

public class UrlUtils {

    /**
     * url添加前缀
     * @param url
     * @return
     */
    public static String addPrefix(String url){

        if (url.startsWith("http://") || url.startsWith("https://")){

            return url;
        }else if(url.startsWith("//")){
            return "http:"+url;

        }else if(url.startsWith("/") && !url.substring(1,2).equals("/")){
            return "http:/"+url;

        }else {
            return "http://"+url;
        }
    }
}
