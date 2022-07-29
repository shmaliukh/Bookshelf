package org.vshmaliukh;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.jndi.toolkit.url.Uri;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.apache.http.client.utils.URIBuilder;
import org.vshmaliukh.terminal.Terminal;

public class SimpleWebApp {
    static ByteArrayOutputStream baos = new ByteArrayOutputStream();
    static PrintWriter printWriter = new PrintWriter(baos, true);
    static Scanner scanner = new Scanner("");
    static private final Terminal terminal = new Terminal(new ScannerWrapper(scanner), printWriter);

    public static void main(String[] args) throws LifecycleException {
        Tomcat tomcat = new Tomcat();
        tomcat.setBaseDir("web_content");
        tomcat.setPort(8080);

        String contextPath = "";
        String docBase = new File(".").getAbsolutePath();

        Context context = tomcat.addContext(contextPath, docBase);

        initTerminal();

        HttpServlet servlet = new HttpServlet() {
            @Override
            protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
                Map<String, String[]> parameterMap = req.getParameterMap();
                parameterMap.forEach((k, v) -> System.out.println(k + "=" + Arrays.toString(v)));

            }

            @Override
            protected void doGet(HttpServletRequest req, HttpServletResponse resp)
                    throws ServletException, IOException {

//                String url = "http://localhost?name=" + urlencode(input) + "&lastName=" + urlencode(lastName);
//                "http://client-s.domain-example/path?inputUrl=client-s.domain-example".replace("client-s.domain-example","server1.domain.com");
//                new URL(url);
                /*
                servers
                server1.domain.com
                server2.domain.com
                 */

//                URL.class.newInstance().toURI().
                resp.setIntHeader("Refresh", 1);

                PrintWriter writer = resp.getWriter();
                String input = req.getParameter("input");
                scanner = new Scanner(input + System.lineSeparator());

                terminal.generateUserInterface();
                terminal.itemGsonHandler.saveToFile(terminal.shelf.getAllLiteratureObjects());


                writer.println("<html><title>Welcome</title><body>");
                writer.println("<h1>" + baos.toString().replaceAll(System.lineSeparator(), "<br>") + "</h1>");
                writer.println("</body></html>");
                baos.reset();
//                URIBuilder uriBuilder = new URIBuilder();
//                uriBuilder.setHost("asdfasd").setScheme().setPort("").addParameter("","").addParameter("dfasdfasd");
//                URIBuilder uriBuilder1 = URIBuilder(url);
//                uriBuilder1.get
            }
        };

        String servletName = "Servlet1";
        String urlPattern = "/terminal/*";
        tomcat.addServlet(contextPath, servletName, servlet);
        context.addServletMappingDecoded(urlPattern, servletName);


        //AddServlet addServlet = new AddServlet();
        //servletName = "AddServer";
        //urlPattern = "/add";
        //tomcat.addServlet(contextPath, servletName, addServlet);
        //context.addServletMappingDecoded(urlPattern, servletName);


        tomcat.start();
        tomcat.getServer().await();
    }

    private static void initTerminal() {
        printWriter.println("Terminal START");
        terminal.startWithUserConfig(false);
        terminal.setUpTypeOfWorkWithFiles();
        terminal.initServicesForTerminal();
        terminal.informAboutFileTypeWork(1);
        terminal.itemGsonHandler.readListFromFile().forEach(terminal.shelf::addLiteratureObject);
    }

}
