package org.vshmaliukh.console_terminal.services.file_service;

public class ContainerForGson {

    String classOfLiterature;
    Object item;

    public ContainerForGson(Object item){
        this.classOfLiterature = item.getClass().getSimpleName();
        this.item = item;
    }
}
