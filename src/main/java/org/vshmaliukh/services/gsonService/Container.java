package org.vshmaliukh.services.gsonService;

public class Container {

    String classOfLiterature;
    Object literature;

    public Container(Object literature){
        this.classOfLiterature = literature.getClass().toString();
        this.literature = literature;
    }
}
