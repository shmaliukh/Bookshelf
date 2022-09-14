package com.vshmaliukh.springwebappmodule;

import org.apache.http.client.utils.URIBuilder;

public final class SpringAppUtils {

    private SpringAppUtils(){}

    public static String generateUrlString(String pageTitle){
        return new URIBuilder().setPath(pageTitle).toString();
    }

}
