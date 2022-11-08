package com.vshmaliukh.httpclientmodule;

public final class MyUtils {

    public static final String COOKIE_HEADER = "Cookie";

    public static String generateCookieValue(String name, String value){
        return name + "=" + value;
    }

}
