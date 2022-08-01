package org.vshmaliukh.web;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.vshmaliukh.terminal.Terminal;

public class SimpleWebApp {

    public static void main(String[] args) throws LifecycleException {
        Tomcat tomcat = new Tomcat();
        tomcat.setBaseDir("web_content");
        tomcat.setPort(8080);

        String contextPath = "";
        String docBase = new File(".").getAbsolutePath();

        Context context = tomcat.addContext(contextPath, docBase);

        String servletName = "book_shelf_servlet";
        String urlPattern = "/book_shelf/*";

        //TerminalThread terminalThread = new TerminalThread();
        //terminalThread.start();

        BookShelfServlet bookShelfServlet = new BookShelfServlet();

        tomcat.addServlet(contextPath, servletName, bookShelfServlet);
        context.addServletMappingDecoded(urlPattern, servletName);

        tomcat.start();
        tomcat.getServer().await();


    }

    static class TerminalThread extends Thread {
        public static final ByteArrayOutputStream BAOS = new ByteArrayOutputStream();
        public static Scanner scanner = new Scanner("");
        public static PrintWriter printWriter = new PrintWriter(BAOS, true);
        public static final Terminal TERMINAL = new Terminal(new ScannerWrapper(scanner), printWriter);

        @Override
        public synchronized void run() {
            try {
                TERMINAL.startWork(false);
            } catch (Exception e) {
                //log.error("[TerminalThread] problem to start thread. Exception: ", e);
            }
        }
    }
}
