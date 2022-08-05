package org.vshmaliukh.web;

public class WebPageBuilder {

    private String title;

    private final StringBuilder bodyStringBuilder = new StringBuilder();


    public WebPageBuilder(String title) {
        this.title = title;
    }

    public void addToBody(StringBuilder bodyItem) {
        bodyStringBuilder.append(bodyItem);
    }

    public void addToBody(String bodyItem) {
        bodyStringBuilder.append(bodyItem.replaceAll(System.lineSeparator(), " <br> "));
    }

    public void addButton(String reference, String label) {
        bodyStringBuilder.append(WebUtils.formHTMLButton(reference, label));
    }

    public StringBuilder bodyStartHTML() {
        return new StringBuilder()
                .append("<body")
                .append(" bgcolor = \"#EAF7CF\"")
                .append(">\n");
    }

    public StringBuilder bodyEndHTML() {
        return new StringBuilder().append("</body>");
    }

    public StringBuilder baseEndHTML() {
        return new StringBuilder().append("</html>");
    }

    public StringBuilder initHeader(String title) {
        return new StringBuilder()
                .append("<head>")
                .append("<meta charset=\" UTF - 8\">")
                .append("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8' />")
                .append("<title>")
                .append(title)
                .append("</title>")
                .append("</head>");
    }

    public void addMessageBlock(String informMessage) {
        if (informMessage != null) {
            bodyStringBuilder.append("<br>\n")
                    .append(informMessage)
                    .append("<br>\n");
        }
    }

    public StringBuilder buildPage() {
        if (title == null) {
            title = "";
        }
        return new StringBuilder()
                .append(initHeader(title))
                .append(bodyStartHTML())

                .append(bodyStringBuilder)

                .append(bodyEndHTML())
                .append(baseEndHTML());
    }
}
