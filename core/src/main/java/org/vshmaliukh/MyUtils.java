package org.vshmaliukh;

public final class MyUtils {

    private MyUtils(){}

    public static String generateCookieValue(String name, Object value){
        return name + "=" + value;
    }

}
