package org.vshmaliukh;

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

    public static final String USER_INPUT = "user_input";

    static ByteArrayOutputStream baos = new ByteArrayOutputStream();
    static PrintWriter printWriter = new PrintWriter(baos, true);
    static Scanner scanner = new Scanner("");

    static Terminal terminal = new Terminal(new ScannerWrapper(scanner), printWriter);

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //System.out.println("doPost");
        //Map<String, String[]> parameterMap = request.getParameterMap();
        //parameterMap.forEach((k, v) -> System.out.println(k + "=" + Arrays.toString(v)));


        doGet(request, response);
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter writer = response.getWriter();
        response.setContentType("text/html");
        String title = "book_shelf";
        WebPageBuilder webPageBuilder = new WebPageBuilder(title);

        terminal.generateUserInterface();
        writer.println("<br>" +baos.toString() + "<br>");
        baos.reset();

        String userRequestParameter = request.getParameter(USER_INPUT);
        terminal.scanner = new ScannerWrapper(new Scanner(userRequestParameter));
        webPageBuilder.addToBody(userRequestParameter + "<br>");

        webPageBuilder.addToBody(
                "<form action = \"HelloForm\" method = \"POST\">\n" +
                        "         User input: <input type = \"text\" name = \""+USER_INPUT+"\">\n" +
                        "         <br />\n" +
                        "         <input type = \"submit\" value = \"Submit\" />\n" +
                        "      </form>"
        );
        writer.println(webPageBuilder.buildPage());
    }
}
