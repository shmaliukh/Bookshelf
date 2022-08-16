package org.vshmaliukh.tomcat_web_app;

import org.vshmaliukh.shelf.Shelf;

public class WebShelfHandler extends SqlLiteShelfHandler {

    public WebShelfHandler(String userName, int typeOfWorkWithFiles) {
        super(userName);
        this.shelf = new Shelf();
    }
}
