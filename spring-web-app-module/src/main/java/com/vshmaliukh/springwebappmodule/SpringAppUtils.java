package com.vshmaliukh.springwebappmodule;

import org.apache.http.client.utils.URIBuilder;

public final class SpringAppUtils {

    private SpringAppUtils(){}

    public static String generateUrlPath(String pageTitle){
        return new URIBuilder().setPath(pageTitle).toString();
    }

}
