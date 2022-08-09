package org.vshmaliukh.tomcat_web_app;

import org.vshmaliukh.shelf.shelf_handler.AbstractShelfHandler;

public class WebShelfHandler extends AbstractShelfHandler {

    public WebShelfHandler(String userName, int typeOfWorkWithFiles) {
        super(userName, typeOfWorkWithFiles);
        this.shelf = new WebShelf();
    }
}
