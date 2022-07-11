package org.vshmaliukh.services;

import com.google.gson.annotations.SerializedName;

public class ContainerForLiteratureObject {
    @SerializedName("ClassType")
    String classOfLiterature;
    @SerializedName("Literature")
    Object literature;

    public ContainerForLiteratureObject(Object literature){
        this.classOfLiterature = literature.getClass().toString();
        this.literature = literature;
    }
}
