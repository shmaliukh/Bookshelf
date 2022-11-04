package com.vshmaliukh.httpclientmodule.http_client_services.apache_http_client_service;

public final class MyApacheUtils {

    public static final String COOKIE_HEADER = "Cookie";

    public static String generateCookieValue(String name, String value){
        return name + "=" + value;
    }

}
