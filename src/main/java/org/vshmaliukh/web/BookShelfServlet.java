package org.vshmaliukh.web;

import org.vshmaliukh.terminal.Terminal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;

public class BookShelfServlet extends HttpServlet {

    public static final String TITLE = "book_shelf";
    public static final String USER_INPUT = "user_input";
    public static final String USER_INPUT_FIELD = "<form action = \"user_interface\" method = \"POST\">\n" +
            "         User input: <input type = \"text\" name = \"" + USER_INPUT + "\">\n" +
            "         <br />\n" +
            "         <input type = \"submit\" value = \"Submit\" />\n" +
            "      </form>";

    static ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PrintWriter printWriter = new PrintWriter(baos, true);
    Scanner scanner = new Scanner("");

        Terminal terminal = new Terminal(new ScannerWrapper(scanner), printWriter);


    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("doPost");
        Map<String, String[]> parameterMap = request.getParameterMap();
        parameterMap.forEach((k, v) -> System.out.println(k + "=" + Arrays.toString(v)));

        doGet(request, response);
    }


    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        WebPageBuilder webPageBuilder = new WebPageBuilder(TITLE);
        PrintWriter writer = response.getWriter();
        response.setContentType("text/html");

        terminal.generateUserInterface();
        printTerminalInterface(writer);

        String userRequestParameter = request.getParameter(USER_INPUT);
        terminal.scanner = new ScannerWrapper(new Scanner(userRequestParameter));
        webPageBuilder.addToBody(userRequestParameter + "<br>");

        webPageBuilder.addToBody(USER_INPUT_FIELD);
        writer.println(webPageBuilder.buildPage());
    }

    private void printTerminalInterface(PrintWriter writer) {
        writer.println("<br>" + baos.toString().replaceAll(System.lineSeparator(), "<br>") + "<br>");
        baos.reset();
    }
}
