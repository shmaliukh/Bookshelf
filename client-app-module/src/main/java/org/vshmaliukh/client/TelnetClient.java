package org.vshmaliukh.client;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

@Slf4j
public class TelnetClient {

    public static final String CLIENT_HOST_ENV = "CLIENT_HOST";
    public static final String CLIENT_PORT_ENV = "CLIENT_PORT";

    private static final String DEFAULT_CLIENT_SOCKET_HOST = "localhost";
    private static final int DEFAULT_CLIENT_SOCKET_PORT = 8080;

    private Socket socket;

    private Scanner socketScanner;
    private Scanner localScanner;
    private PrintWriter socketPrintWriter;
    private PrintWriter localPrintWriter;

    private Thread inThread;
    private Thread outThread;

    public TelnetClient() throws IOException {

        setUpSocket();

        socketScanner = new Scanner(socket.getInputStream());
        localScanner = new Scanner(System.in);
        socketPrintWriter = new PrintWriter(socket.getOutputStream(), true);
        localPrintWriter = new PrintWriter(System.out, true);

        localPrintWriter.println("Client start");

        inThread = new ScannerToWriterRedirector(socketScanner, localPrintWriter);
        outThread = new ScannerToWriterRedirector(localScanner, socketPrintWriter);

        inThread.start();
        outThread.start();
    }

    void setUpSocket() {
        String host = setUpHost();
        int port = setUpPort();
        try {
            socket = new Socket(host, port);
        } catch (IOException ioe) {
            log.error("[Client] Problem to set up socket: " + System.lineSeparator() +
                    "   host = " + host + System.lineSeparator() +
                    "   port = " + port + System.lineSeparator(), ioe);
        }
    }

    private static String setUpHost() {
        String host;
        String hostStrFromEnv = System.getenv(CLIENT_HOST_ENV);
        log.info("[Client] host = ");
        if (StringUtils.isNotBlank(hostStrFromEnv)) {
            host = hostStrFromEnv;
            log.info(host + " // got from system environment variables by '" + CLIENT_HOST_ENV + "' name");
        } else {
            host = DEFAULT_CLIENT_SOCKET_HOST;
            log.info(host + " // got from default value");
        }
        return host;
    }

    private static int setUpPort() {
        int port = 0;
        String portStrFromEnv = System.getenv(CLIENT_PORT_ENV);
        if (StringUtils.isNotBlank(portStrFromEnv)) {
            try {
                port = Integer.parseInt(portStrFromEnv);
                log.info(port + " // got from system environment variables by '" + CLIENT_PORT_ENV + "' name");
            } catch (NumberFormatException nfe) {
                log.error("problem to parse 'port' value got from system environment variables by '" + CLIENT_PORT_ENV + "' name", nfe);
            }
        } else {
            port = DEFAULT_CLIENT_SOCKET_PORT;
            log.info(port + " // got from default value");
        }
        return port;
    }

    public static void main(String[] args) throws IOException {
        TelnetClient client = new TelnetClient();
    }
}