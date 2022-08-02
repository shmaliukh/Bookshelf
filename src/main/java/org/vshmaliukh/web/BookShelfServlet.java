package org.vshmaliukh.web;

import org.vshmaliukh.terminal.Terminal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;

import static org.vshmaliukh.web.SimpleWebApp.BAOS;
import static org.vshmaliukh.web.SimpleWebApp.TERMINAL;

public class BookShelfServlet extends HttpServlet {

    public static final String TITLE = "book_shelf";
    public static final String USER_INPUT = "user_input";
    public static final String USER_INPUT_FORM =
            "<form action = \"user_interface\" method = \"POST\">\n" +
            "         <input type = \"text\" name = \"" + USER_INPUT + "\">\n" +
            "         <br />\n" +
            "         <input type = \"submit\" value = \"Submit\" />\n" +
            "      </form>";

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("doPost");
        Map<String, String[]> parameterMap = request.getParameterMap();
        parameterMap.forEach((k, v) -> System.out.println(k + "=" + Arrays.toString(v)));

        String userRequestParameter = request.getParameter(USER_INPUT);
        //TERMINAL.scanner = new ScannerWrapper(new Scanner(userRequestParameter));

        doGet(request, response);
    }


    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        WebPageBuilder webPageBuilder = new WebPageBuilder(TITLE);
        PrintWriter writer = response.getWriter();
        response.setContentType("text/html");

        TERMINAL.startWithUserConfig(false);
        //TERMINAL.setUpTypeOfWorkWithFiles();
        TERMINAL.generateUserInterface();

        //TERMINAL.printWriter = writer;

        //String userRequestParameter = request.getParameter(USER_INPUT);
        //TERMINAL.scanner = new ScannerWrapper(new Scanner(userRequestParameter));
//        if(userRequestParameter != null){
//
//            webPageBuilder.addToBody(userRequestParameter + "<br>");
//        }

        webPageBuilder.addToBody(USER_INPUT_FORM);
        writer.println(webPageBuilder.buildPage());

        printTerminalInterface(writer);
    }

    private void printTerminalInterface(PrintWriter writer) {
        String message = BAOS.toString().replaceAll(System.lineSeparator(), " <br> ");
        writer.println("<br>" + message + "<br>");
        BAOS.reset();
    }
}
