package org.vshmaliukh;

public final class BootstrapHtmlBuilder {

    private BootstrapHtmlBuilder() {
    }

    public static String div(String text) {
        return "<div>" + text + "</div>";
    }

    public static String div(String divCustomization, String text) {
        return "<div " + divCustomization + ">" + text + "</div>";
    }

    public static String buttonWithRefAndCustomClassStyle(String label, String ref, String style) {
        return "<button type=\"button\" onclick=\"location.href='/" + ref + "'\" " +
                "class=\"" + style + "\">" + label + "</button>\n" +
                "<br>";
    }

    public static String input(String type, String name, String placeholder, String value) {
        return "<input type=\"" + type + "\" " +
                "name=\"" + name + "\" " +
                "placeholder=\"" + placeholder + "\" " +
                "class=\"form-control\"" +
                "value=\"" + value + "\"" +
                "required>";
    }

}
