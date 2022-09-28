package org.vshmaliukh;

import org.apache.http.client.utils.URIBuilder;

public final class BootstrapHtmlBuilder {

    public static final String SUBMIT_BUTTON_DEFAULT_TEXT = "Submit";

    private BootstrapHtmlBuilder() {
    }

    public static String description(String str) {
        return "<p class=\"lead\">" + str + "</p>";
    }

    public static String radioButton(String label, String id, String name, boolean checked) {
        StringBuilder sb = new StringBuilder();
        sb.append("<div class=\"form-check\">\n")
                .append("<input id=\"typeOfWorkRadioButtonId-").append(id)
                .append("\" name=\"").append(name)
                .append("\" type=\"radio\" class=\"form-check-input\" value=\"" + id + "\"");
        if (checked) {
            sb.append(" checked=\"\" ");
        }
        sb.append("required=\"\">\n")
                .append("<label class=\"form-check-label\" ")
                .append("for=\"typeOfWorkRadioButtonId-").append(id).append("\">")
                .append(label)
                .append("</label>\n")
                .append("</div>");
        return sb.toString();
    }
    //https://getbootstrap.com/docs/5.2/examples/list-groups/# TODO use better style for radio buttons

    public static String radioButton(String label, String id, String name, boolean checked, String value) {
        StringBuilder sb = new StringBuilder();
        sb.append("<div class=\"form-check\">\n")
                .append("<input id=\"typeOfWorkRadioButtonId-").append(id).append("\"")
                .append("name=\"").append(name).append("\"")
                .append("type=\"radio\" class=\"form-check-input\" value=\"" + id + "\"")
                .append("value=\"").append(value).append("\"");
        if (checked) {
            sb.append(" checked=\"\" ");
        }
        sb.append("required=\"true\">\n")
                .append("<label class=\"form-check-label\" ")
                .append("for=\"typeOfWorkRadioButtonId-").append(id).append("\">")
                .append(label)
                .append("</label>\n")
                .append("</div>");
        return sb.toString();
    }

//    public static String checkBox(){
//        return "<div class=\"form-check\">\n" +
//                "        <input type=\"checkbox\" class=\"form-check-input\" id=\"same-address\">\n" +
//                "        <label class=\"form-check-label\" for=\"same-address\">Shipping address is the same as my billing address</label>\n" +
//                "    </div>";
//    }

    public static String formSubmitButton() {
        return breaker() +
                "<button class=\"w-100 btn btn-primary btn-lg\" type=\"submit\">" +
                SUBMIT_BUTTON_DEFAULT_TEXT +
                "</button>";
    }

    public static String formSubmitButton(String label) {
        return breaker() +
                "<button class=\"w-100 btn btn-primary btn-lg\" type=\"submit\">" +
                label +
                "</button>";
    }

    public static String divContainer(String containerStr) {
        return "<div class=\"container mt-5 mb-5\">" + containerStr + "</div>";
    }

    public static String form(String pageToSend, String method, String formItems, String submitButton) {
        return "<form action=\"/" + pageToSend + "\" method=\"" + method + "\">\n" +
                formItems +
                submitButton +
                "</form>";
    }

    public static String form(String id, String formItems, String submitButton) {
        return "<form id=\"" + id + "\">\n" +
                formItems +
                submitButton +
                "</form>";
    }

    public static String form(URIBuilder uriBuilder, String method, String formItems, String submitButton) {
        return "<form action=\"" + uriBuilder.toString() + "\" method=\"" + method + "\">\n" +
                formItems +
                submitButton +
                "</form>";
    }

    public static String split() {
        return "<hr class=\"my-4\">";
    }

    public static String breaker() {
        return "<br>";
    }

    public static String htext(String text, String level) {
        return "<h" + level + ">" + text + "</h" + level + ">";
    }

    public static String div(String text) {
        return "<div>" + text + "</div>";
    }

    public static String div(String divCustomization, String text) {
        return "<div " + divCustomization + ">" + text + "</div>";
    }

    public static String buttonWithRef(String label, String ref) {
        return "<button type=\"button\" onclick=\"location.href='/" + ref + "'\" " +
                "class=\"w-100 btn btn-lg btn-outline-primary\">" + label + "</button>\n" +
                "<br>";
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

    public static String logInInputUserName(String name, String value) {
        return "<div class=\"input-group has-validation\">\n" +
                "                    <span class=\"input-group-text\">@</span>\n" +
                "                    <input type=\"text\" " +
                "                       name=\"" + name + "\" " +
                "                       class=\"form-control\" " +
                "                       id=\"userName\" " +
                "                       placeholder=\"Username\" " +
                "                       value=\"" + value + "\" " +
                "                       required=\"\">\n" +
//                "                    <div class=\"invalid-feedback\">\n" +
//                "                        Your username is required.\n" +
//                "                    </div>\n" +
                "                </div>";
    }

    public static String cardBlue(String label, String cardContentStr) {
        return "<div class=\"card mb-4 rounded-3 shadow-sm border-primary\">" +
                "   <div class=\"card-header py-3 text-bg-primary border-primary\">\n" +
                "       <h4 class=\"my-0 fw-normal\">" + label + "</h4>\n" +
                "   </div>" +
                "   <div class=\"card-body\">" + cardContentStr + "</div>" +
                "</div>";
    }

    public static String cardWhite(String label, String cardContentStr) {
        return "<div class=\"card mb-4 rounded-3 shadow-sm\">" +
                "   <div class=\"card-header py-3\">\n" +
                "       <h4 class=\"my-0 fw-normal\">" + label + "</h4>\n" +
                "   </div>" +
                "   <div class=\"card-body\">" + cardContentStr + "</div>" +
                "</div>";
    }

    public static String cardWhite(String htext, String hlevel, String cardContentStr) {
        return "<div class=\"card mb-4 rounded-3 shadow-sm\">" +
                "   <div class=\"card-header py-3\">\n" +
                "       <h" + hlevel + " class=\"my-0 fw-normal\">" + htext + "</h" + hlevel + ">\n" +
                "   </div>" +
                "   <div class=\"card-body\">" + cardContentStr + "</div>" +
                "</div>";
    }
}
