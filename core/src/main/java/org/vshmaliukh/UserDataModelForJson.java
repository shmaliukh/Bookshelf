package org.vshmaliukh;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDataModelForJson {

    private String userName;
    private int typeOfWork;

    public String getTypeOfWorkAsStr(){
        return String.valueOf(getTypeOfWork());
    }

}
