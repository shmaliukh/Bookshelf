package org.vshmaliukh;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class BookShelfServlet extends HttpServlet {



    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {



        PrintWriter writer = response.getWriter();
        writer.println("<html><title>Addition</title><body>");
        writer.println("<h1>" + result + "</h1");
        writer.println("</body></html>");


    }
}
