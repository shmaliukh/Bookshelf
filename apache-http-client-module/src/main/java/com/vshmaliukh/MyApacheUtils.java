package com.vshmaliukh;

public final class MyApacheUtils {

    public static final String COOKIE_HEADER = "Cookie";

    public static String generateCookieValue(String name, String value){
        return name + "=" + value;
    }

}
