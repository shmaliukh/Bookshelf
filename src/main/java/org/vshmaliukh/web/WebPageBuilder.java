package org.vshmaliukh.web;

import lombok.Data;

@Data
public class WebPageBuilder {

    private String title;

    private StringBuilder sb = new StringBuilder(); // TODO rename (sb = buffer StringBuilder)
    private StringBuilder body = new StringBuilder();


    public WebPageBuilder(String title) {
        this.title = title;
    }

    public void addToBody(StringBuilder bodyItem) {
        body.append(bodyItem);
    }

    public void addToBody(String bodyItem) {
        body.append(bodyItem);
    }

    public void addButton(String reference, String label) {
        body.append(" <br> \n" +
                "<button " +
                "onclick=\"window.location.href='" +
                reference +
                "';\"> " +
                label +
                "</button> \n");
    }

    public StringBuilder bodyStartHTML() {
        return new StringBuilder().append("<body" +
                //" bgcolor = \\\"#DBF4AD\\\"" + // TODO use color background
                ">");
    }

    public StringBuilder bodyEndHTML() {
        return new StringBuilder().append("</body>");
    }

    public StringBuilder baseStartHTML() {
        sb = new StringBuilder();
        sb.append("<!DOCTYPE html>");
        sb.append("<html lang=\" en\">");
        return sb;
    }

    public StringBuilder baseEndHTML() {
        return new StringBuilder().append("</html>");
    }

    public StringBuilder initHeader(String title) {
        sb = new StringBuilder();
        sb.append("<head>");
        sb.append("<meta charset=\" UTF - 8\">");
        sb.append("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8' />");
        sb.append("<title>" + title + "</title>");
        sb.append("</head>");
        return sb;
    }

    public StringBuilder buildPage() {
        if (title == null) {
            title = "";
        }

        sb = new StringBuilder();
        return sb.append(this.initHeader(title))
                .append(bodyStartHTML())

                .append(getBody())

                .append(bodyEndHTML())
                .append(baseEndHTML());
    }
}
