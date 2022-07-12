package org.vshmaliukh.services.gson_service;

public class Container {

    String classOfLiterature;
    Object literature;

    public Container(Object literature){
        this.classOfLiterature = literature.getClass().toString();
        this.literature = literature;
    }
}
