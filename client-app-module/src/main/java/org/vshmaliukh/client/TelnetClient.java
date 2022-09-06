package org.vshmaliukh.client;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

@Slf4j
public class TelnetClient extends AbstractClient {

    public static final String SERVICE_NAME = "TelnetClient";

    public TelnetClient() {
        serviceName = SERVICE_NAME;

        setUpSocket();

        setUpSocketScanner();
        setUpSocketPrintWriter();

        setUpLocalScanner();
        setUpLocalPrintWriter();
    }

    void startWork() {
        localPrintWriter.println(serviceName + " start");

        Thread inThread = new ScannerToWriterRedirector(socketScanner, localPrintWriter);
        Thread outThread = new ScannerToWriterRedirector(localScanner, socketPrintWriter);

        inThread.start();
        outThread.start();
    }

    @Override
    void setUpSocketScanner() {
        try {
            socketScanner = new Scanner(socket.getInputStream());
        } catch (Exception e) {
            logError("Problem to set up socket scanner", e);
        }
    }

    @Override
    void setUpSocketPrintWriter() {
        try {
            socketPrintWriter = new PrintWriter(socket.getOutputStream(), true);
        } catch (Exception e) {
            logError("Problem to set up socket print writer", e);
        }
    }

    @Override
    void setUpSocket() {
        setUpHost();
        setUpPort();
        try {
            socket = new Socket(host, port);
        } catch (IOException ioe) {
            logError("Problem to set up socket: " + System.lineSeparator() +
                    "   host = " + host + System.lineSeparator() +
                    "   port = " + port + System.lineSeparator(), ioe);
        }
    }

    @Override
    void setUpHost() {

        String hostStrFromEnv = System.getenv(CLIENT_HOST_ENV);
        if (StringUtils.isNotBlank(hostStrFromEnv)) {
            host = hostStrFromEnv;
            logInfo("host = " + host + " // got from system environment variables by '" + CLIENT_HOST_ENV + "' name");
        } else {
            host = DEFAULT_CLIENT_SOCKET_HOST;
            logInfo("host = " + host + " // got from default value");
        }
    }

    @Override
    void setUpPort() {
        String portStrFromEnv = System.getenv(CLIENT_PORT_ENV);
        if (StringUtils.isNotBlank(portStrFromEnv)) {
            try {
                port = Integer.parseInt(portStrFromEnv);
                logInfo("port = " + port + " // got from system environment variables by '" + CLIENT_PORT_ENV + "' name");
            } catch (NumberFormatException nfe) {
                logError("Problem to parse 'port' value got from system environment variables by '" + CLIENT_PORT_ENV + "' name", nfe);
            }
        } else {
            port = DEFAULT_CLIENT_SOCKET_PORT;
            logInfo("port = " + port + " // got from default value");
        }
    }

    public static void main(String[] args) {
        new TelnetClient().startWork();
    }
}