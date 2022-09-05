package org.vshmaliukh;

import org.apache.catalina.LifecycleException;
import org.vshmaliukh.tomcat_web_app.ShelfWebApp;

public class Main {
    public static void main(String[] args) {
        try {
            ShelfWebApp.main(args);
        } catch (LifecycleException e) {
            throw new RuntimeException(e);
        }
    }
}
