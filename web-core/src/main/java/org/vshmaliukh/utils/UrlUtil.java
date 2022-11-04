package org.vshmaliukh.utils;

import org.apache.http.client.utils.URIBuilder;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

import static org.vshmaliukh.Constants.USER_PARAMETER_LIST;

public final class UrlUtil {

    private UrlUtil(){}

    public static void redirectTo(String servletTitle, HttpServletResponse response, Map<String, String> userAtr) {
        try {
            response.sendRedirect(generateBaseURLString(servletTitle, userAtr));
        } catch (IOException ioe) {
            WebUtils.logServletErr(servletTitle, ioe);
        }
    }

    public static void redirectTo(String servletTitle, HttpServletResponse response, URIBuilder uriBuilder) {
        try {
            response.sendRedirect(uriBuilder.toString());
        } catch (IOException ioe) {
            WebUtils.logServletErr(servletTitle, ioe);
        }
    }

    public static String generateBaseURLString(String servletTitle, Map<String, String> userAtr) {
        return generateBaseURLBuilder(servletTitle, userAtr).toString();
    }

    public static URIBuilder generateBaseURLBuilder(String servletTitle, Map<String, String> userAtr) {
        URIBuilder uriBuilder = new URIBuilder().setPath(servletTitle);
        for (String parameter : USER_PARAMETER_LIST) {
            uriBuilder.addParameter(parameter, userAtr.get(parameter));
        }
        return uriBuilder;
    }
}
