package com.vshmaliukh.springwebappmodule;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDataModelForJson {

    String userName;
    int typeOfWork;

    public String getTypeOfWorkAsStr(){
        return String.valueOf(getTypeOfWork());
    }

}
