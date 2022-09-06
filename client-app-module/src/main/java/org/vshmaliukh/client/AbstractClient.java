package org.vshmaliukh.client;

import lombok.extern.slf4j.Slf4j;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

@Slf4j
public abstract class AbstractClient {

    public static final String CLIENT_HOST_ENV = "CLIENT_HOST";
    public static final String CLIENT_PORT_ENV = "CLIENT_PORT";

    public static final String DEFAULT_SERVICE_NAME = "CLIENT";
    public static final String DEFAULT_CLIENT_SOCKET_HOST = "localhost";
    public static final int DEFAULT_CLIENT_SOCKET_PORT = 8080;

    protected String serviceName = DEFAULT_SERVICE_NAME;

    protected Socket socket;
    protected int port;
    protected String host;

    protected Scanner socketScanner;
    protected Scanner localScanner;
    protected PrintWriter socketPrintWriter;
    protected PrintWriter localPrintWriter;

    abstract void startWork();

    abstract void setUpSocket();

    abstract void setUpPort();

    abstract void setUpHost();

    abstract void setUpSocketScanner();

    abstract void setUpSocketPrintWriter();

    void setUpLocalScanner(){
        localScanner = new Scanner(System.in);
    }

    void setUpLocalPrintWriter(){
        localPrintWriter = new PrintWriter(System.out, true);
    }

    void logError(String errorMessage, Exception e) {
        log.error("[" + serviceName + "] error: " + System.lineSeparator() + errorMessage, e);
    }

    void logInfo(String infoMessage) {
        log.info("[" + serviceName + "] info: " + infoMessage);
    }

}
