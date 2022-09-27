package org.vshmaliukh;

public final class JavaScriptBuilder {

    private JavaScriptBuilder() {
    }

    public static String script(String scriptBody) {
        return "<script>" +
                scriptBody +
                "</script>";
    }

}
