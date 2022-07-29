package org.vshmaliukh;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class AddServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int a, b;
        a = Integer.parseInt(request.getParameter("a"));
        b = Integer.parseInt(request.getParameter("b"));
        int sum = a + b;

        String result = a + " + " + b + " = " + sum;

        PrintWriter writer = response.getWriter();
        writer.println("<html><title>Addition</title><body>");
        writer.println("<h1>" + result + "</h1");
        writer.println("</body></html>");
    }
}
