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

    public static final String LOG_IN_TITLE = "log_in";
    public static final String SELECT_WORK_WITH_FILES_TITLE = "select_work_with_files";
    public static final String MAIN_MENU_TITLE = "main_menu";
    public static final String PRINT_SHELF_TITLE = "print_shelf";

    public static ByteArrayOutputStream BAOS = new ByteArrayOutputStream();
    public static Scanner scanner = new Scanner("");
    public static PrintWriter printWriter = new PrintWriter(BAOS, true);
    public static Terminal TERMINAL = new Terminal(new ScannerWrapper(scanner), printWriter);

    public static void main(String[] args) throws LifecycleException {
        Tomcat tomcat = new Tomcat();
        tomcat.setBaseDir("web_content");
        tomcat.setPort(8080);

        String contextPath = "";
        String docBase = new File(".").getAbsolutePath();
        Context context = tomcat.addContext(contextPath, docBase);

        tomcat.addServlet(contextPath, LOG_IN_TITLE + "_servlet", new LogInServlet());
        context.addServletMappingDecoded('/' + LOG_IN_TITLE, LOG_IN_TITLE + "_servlet");

        tomcat.addServlet(contextPath, SELECT_WORK_WITH_FILES_TITLE + "_servlet", new LogInServlet());
        context.addServletMappingDecoded('/' + SELECT_WORK_WITH_FILES_TITLE, SELECT_WORK_WITH_FILES_TITLE + "_servlet");

        tomcat.addServlet(contextPath, MAIN_MENU_TITLE + "_servlet", new MainMenuServlet());
        context.addServletMappingDecoded('/' + MAIN_MENU_TITLE,  MAIN_MENU_TITLE + "_servlet");

        tomcat.addServlet(contextPath, PRINT_SHELF_TITLE + "_servlet", new PrintShelfServlet());
        context.addServletMappingDecoded('/' + PRINT_SHELF_TITLE, PRINT_SHELF_TITLE + "_servlet");


        tomcat.start();
        tomcat.getServer().await();
    }
}
