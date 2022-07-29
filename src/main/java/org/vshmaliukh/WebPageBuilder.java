package org.vshmaliukh;

public class WebPageBuilder {
    StringBuilder stringBuilder = new StringBuilder();
    StringBuilder sb = new StringBuilder();


    public WebPageBuilder(String title) {

    }

    public StringBuilder bodyStartHTML() {
        return new StringBuilder().append("<body>");
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
        sb.append("<title> " + title + "</title>");
        sb.append("<base href=\" file:///C:/Users/zskat/Vlad_projects/Practice_1/\">"); // TODO get abs path
        sb.append("</head>");
        return sb;
    }


    StringBuilder buildPage(String title) {
        sb = new StringBuilder();
        sb.append(baseStartHTML())
                .append(baseStartHTML())
                .append(initHeader(title))
                .append(bodyEndHTML())
                .append(baseEndHTML());


        sb.append("<a href=\" html / add.html\">Add item</a><br>");
        sb.append("<a href=\"\">Borrow item</a><br>");
        sb.append("<a href=\"\">Arrive item</a><br>");

        return sb;
    }

}
