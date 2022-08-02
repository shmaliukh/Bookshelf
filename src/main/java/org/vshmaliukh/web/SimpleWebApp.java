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

        String servletName = "book_shelf_servlet";
        String urlPattern = "/book_shelf/*";

        //TerminalThread terminalThread = new TerminalThread();
        //terminalThread.start();

        BookShelfServlet bookShelfServlet = new BookShelfServlet();
        tomcat.addServlet(contextPath, servletName, bookShelfServlet);
        context.addServletMappingDecoded(urlPattern, servletName);

        tomcat.addServlet(contextPath, "log_in_servlet", new LogInServlet());
        context.addServletMappingDecoded("/book_shelf/" + LOG_IN_TITLE, "log_in_servlet");

        tomcat.addServlet(contextPath, "select_work_with_files_servlet", new WorkWithFilesServlet());
        context.addServletMappingDecoded("/book_shelf/"+ SELECT_WORK_WITH_FILES_TITLE, "select_work_with_files_servlet");

        tomcat.addServlet(contextPath, "main_menu_servlet", new MainMenuServlet());
        context.addServletMappingDecoded("/book_shelf/"+ MAIN_MENU_TITLE, "main_menu_servlet");

        tomcat.start();
        tomcat.getServer().await();
    }
}
