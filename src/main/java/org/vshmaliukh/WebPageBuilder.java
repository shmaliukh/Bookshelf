package org.vshmaliukh;

import lombok.Data;

@Data
public class WebPageBuilder {

    String title;

    StringBuilder sb = new StringBuilder(); // TODO rename (buffer StringBuilder)
    StringBuilder body = new StringBuilder();


    public WebPageBuilder(String title) {
        this.title = title;
    }

    public void addToBody(StringBuilder bodyItem) {
        body.append(bodyItem);
    }

    public void addToBody(String bodyItem) {
        body.append(bodyItem);
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
        sb.append("<title>" + title + "</title>");
        //sb.append("<base href=\" file:///C:/Users/zskat/Vlad_projects/Practice_1/\">"); // TODO get abs path
        sb.append("</head>");
        return sb;
    }

    StringBuilder buildPage() {
        if(title == null){
            title = "";
        }

        sb = new StringBuilder();
        return sb.append(this.initHeader(title))
                .append(bodyStartHTML())

                .append(getBody())

                .append(bodyEndHTML())
                .append(baseEndHTML());
        //return sb;
    }
}
