package org.vshmaliukh.web;

import org.apache.http.client.utils.URIBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.vshmaliukh.web.LogInServlet.TYPE_OF_WORK_WITH_FILES;
import static org.vshmaliukh.web.LogInServlet.USER_NAME;

public class WebUtils {

    public static void redirectTo(String servletTitle, HttpServletResponse response, HttpServletRequest request) throws IOException {
        response.sendRedirect(generateURLString(servletTitle, request));
    }

    public static String generateURLString(String servletTitle, HttpServletRequest request) {
        return new URIBuilder().setPath(servletTitle)
                .addParameter(USER_NAME, request.getParameter(USER_NAME))
                .addParameter(TYPE_OF_WORK_WITH_FILES, request.getParameter(TYPE_OF_WORK_WITH_FILES))
                .toString();
    }
}
