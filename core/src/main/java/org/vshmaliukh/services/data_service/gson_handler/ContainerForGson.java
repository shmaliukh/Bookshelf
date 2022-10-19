package org.vshmaliukh.services.data_service.gson_handler;

public class ContainerForGson {

    String classOfLiterature;
    Object item;

    public ContainerForGson(Object item){
        this.classOfLiterature = item.getClass().getSimpleName();
        this.item = item;
    }
}
