package org.vshmaliukh;

import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.LifecycleException;
import org.vshmaliukh.tomcat_web_app.ShelfWebApp;

@Slf4j
public class Main {
    public static void main(String[] args) {
        try {
            ShelfWebApp.main(args);
        } catch (LifecycleException le) {
            log.error("Problem to run web app: " + le.getMessage(), le);
        }
    }
}
