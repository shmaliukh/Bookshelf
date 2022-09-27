package org.vshmaliukh;

public final class JavaScriptBuilder {

    private JavaScriptBuilder() {
    }

    public static String script(String scriptBody) {
        return "<script>" +
                scriptBody +
                "</script>";
    }

    public static String formOnSubmit(String formElemId, String methodBody) {
        return formElemId + ".onsubmit = async (e) => {\n" +
                "   e.preventDefault();\n" +
                methodBody +
                "};";
    }

    public static String sendFormDataAsJson(String formElemId,
                                            String pageToSend,
                                            String method,
                                            String pageToRedirect) {
        return "let form = document.getElementById('" + formElemId + "');\n" +
                "let formData = new FormData(form);\n" +
                "let formValue = Object.fromEntries(formData);\n" +
                "let jsonBody = JSON.stringify(formValue);\n" +
                "fetch(\"/" + pageToSend + "\", {\n" +
                "    method: \"" + method + "\",\n" +
                "    body: jsonBody,\n" +
                "    headers: {\n" +
                "        \"Content-Type\": \"application/json\"\n" +
                "    }\n" +
                "})" +
                ".then((res) => {\n" +
                "   if (res.ok) window.location.href = '/" + pageToRedirect + "';\n" +
                "});";
    }

}
