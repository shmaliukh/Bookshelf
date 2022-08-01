package org.vshmaliukh;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Map;

public class BookShelfServlet extends HttpServlet {


    public static final String USER_INPUT = "user_input";

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("doPost");
        Map<String, String[]> parameterMap = request.getParameterMap();
        parameterMap.forEach((k, v) -> System.out.println(k + "=" + Arrays.toString(v)));

        doGet(request, response);
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter writer = response.getWriter();
        String title = "book_shelf";
        WebPageBuilder webPageBuilder = new WebPageBuilder(title);

        webPageBuilder.addToBody(request.getParameter(USER_INPUT) + "<br>");




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
