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

    public static final String MODULE_TO_START_ENV = "MODULE_TO_START";

    public static final String CONSOLE_MODULE = ShelfConsoleApp.MODULE_NAME;
    public static final String CLIENT_MODULE = TelnetClient.MODULE_NAME;
    public static final String SERVER_MODULE = MultithreadedSocketServer.MODULE_NAME;
    public static final String WEB_MODULE = ShelfWebApp.MODULE_NAME;

    public static void main(String[] args) throws LifecycleException, IOException {
        String typeOfWork = System.getenv(MODULE_TO_START_ENV);
        runAppByTypeOfWork(typeOfWork, args);
    }

    static void runAppByTypeOfWork(String typeOfWork, String[] args) throws LifecycleException, IOException {
        if (typeOfWork == null || typeOfWork.length() == 0) {
            ShelfWebApp.main(args);
        } else {
            switch (typeOfWork) {
                case CONSOLE_MODULE:
                    ShelfConsoleApp.main(args);
                    break;
                case CLIENT_MODULE:
                    TelnetClient.main(args);
                    break;
                case SERVER_MODULE:
                    MultithreadedSocketServer.main(args);
                    break;
                case WEB_MODULE:
                    ShelfWebApp.main(args);
                    break;
                default:
                    ShelfWebApp.main(args);
                    break;
            }
        }
    }
}