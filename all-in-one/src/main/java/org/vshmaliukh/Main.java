package org.vshmaliukh;

import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.LifecycleException;
import org.vshmaliukh.client.TelnetClient;
import org.vshmaliukh.console_terminal_app.ShelfConsoleApp;
import org.vshmaliukh.server.MultithreadedSocketServer;
import org.vshmaliukh.tomcat_web_app.ShelfWebApp;

import java.io.IOException;

@Slf4j
public class Main {

    public static final String APP_TYPE_OF_WORK_ENV = "APP_TYPE_OF_WORK";

    public static final String CONSOLE_TYPE = "console";
    public static final String CLIENT_TYPE = "client";
    public static final String SERVER_TYPE = "server";
    public static final String WEB_TYPE = "web";

    public static void main(String[] args) throws LifecycleException, IOException {
        String typeOfWork = System.getenv(APP_TYPE_OF_WORK_ENV);
        runAppByTypeOfWork(typeOfWork, args);
    }

    static void runAppByTypeOfWork(String typeOfWork, String[] args) throws LifecycleException, IOException {
        if (typeOfWork == null || typeOfWork.length() == 0) {
            ShelfWebApp.main(args);
        } else {
            switch (typeOfWork.toLowerCase()) {
                case CONSOLE_TYPE:
                    ShelfConsoleApp.main(args);
                    break;
                case CLIENT_TYPE:
                    TelnetClient.main(args);
                    break;
                case SERVER_TYPE:
                    MultithreadedSocketServer.main(args);
                    break;
                case WEB_TYPE:
                    ShelfWebApp.main(args);
                    break;
                default:
                    ShelfWebApp.main(args);
                    break;
            }
        }
    }
}