package org.ShmaliukhVlad.serices;

import com.google.gson.annotations.SerializedName;

public class ContainerForLiteratureObject {
    @SerializedName("ClassType")
    String classOfLiterature;
    @SerializedName("Literature")
    Object literature;

    public ContainerForLiteratureObject(String classOfLiterature, Object literature){
        this.classOfLiterature = classOfLiterature;
        this.literature = literature;
    }

    public ContainerForLiteratureObject(Object literature){
        this.classOfLiterature = literature.getClass().toString();
        this.literature = literature;
    }
}
