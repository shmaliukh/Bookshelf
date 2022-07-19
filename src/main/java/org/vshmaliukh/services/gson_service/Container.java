package org.vshmaliukh.services.gson_service;

public class Container {

    String classOfLiterature;
    Object item;

    public Container(Object item){
        this.classOfLiterature = item.getClass().getSimpleName();
        this.item = item;
    }
}
