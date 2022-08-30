package org.vshmaliukh;

import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.LifecycleException;
import org.vshmaliukh.client_server_app.ShelfClientServerApp;
import org.vshmaliukh.client_server_app.server.MultithreadedSocketServer;
import org.vshmaliukh.tomcat_web_app.ShelfWebApp;

import java.io.IOException;

@Slf4j
public class Main {

    public static final String APP_TYPE_OF_WORK_ENV = "APP_TYPE_OF_WORK";

    public static final String CONSOLE_TYPE = "console";
    public static final String SERVER_CLIENT_TYPE = "server-client";
    public static final String WEB_TYPE = "web";

    public static void main(String[] args) throws LifecycleException, IOException {
        String typeOfWork = System.getenv(APP_TYPE_OF_WORK_ENV);
        runAppByTypeOfWork(typeOfWork);
    }

    static void runAppByTypeOfWork(String typeOfWork) throws LifecycleException, IOException {
        switch (typeOfWork.toLowerCase()) {
            case CONSOLE_TYPE:
                ShelfClientServerApp.main(null);
                break;
            case SERVER_CLIENT_TYPE:
                MultithreadedSocketServer.main(null);
                break;
            case WEB_TYPE:
                ShelfWebApp.main(null);
                break;
            default:
                ShelfClientServerApp.main(null);
                break;
        }
    }
}