package com.vshmaliukh.springwebappmodule.conrollers;

import java.util.HashMap;
import java.util.Map;

import static org.vshmaliukh.Constants.TYPE_OF_WORK_WITH_FILES;
import static org.vshmaliukh.Constants.USER_NAME;

public class ControllerUtils {

    public static Map<String, String> adaptUserAtrToWebAppStandard(String userName, int typeOfWork) {
        Map<String, String> userAtrMap = new HashMap<>();
        userAtrMap.put(USER_NAME, userName);
        userAtrMap.put(TYPE_OF_WORK_WITH_FILES, String.valueOf(typeOfWork));
        return userAtrMap;
    }

}
