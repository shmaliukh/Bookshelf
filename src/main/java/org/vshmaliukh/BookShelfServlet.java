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


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String[]> parameterMap = req.getParameterMap();
        parameterMap.forEach((k, v) -> System.out.println(k + "=" + Arrays.toString(v)));

    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter writer = response.getWriter();
        String title = "book_shelf";
        WebPageBuilder webPageBuilder = new WebPageBuilder(title);




        webPageBuilder.addToBody(
                "<form action=\"\" method=\"get\" target=\"_blank\">\n" +
                        "User input: <input type=\"text\" name=\"user_input\">\n" +
                        "<input type=\"button\" value=\"Submit\">\n" +
                        "</form>"
        );
        writer.println(webPageBuilder.buildPage());
    }
}
