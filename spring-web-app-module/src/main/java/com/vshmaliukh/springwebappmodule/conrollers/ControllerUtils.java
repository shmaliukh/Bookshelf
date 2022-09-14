package com.vshmaliukh.springwebappmodule.conrollers;

import org.vshmaliukh.ConfigFile;

import java.util.HashMap;
import java.util.Map;

import static com.vshmaliukh.springwebappmodule.BootstrapHtmlBuilder.radioButton;
import static org.vshmaliukh.Constants.TYPE_OF_WORK_WITH_FILES;
import static org.vshmaliukh.Constants.USER_NAME;

public class ControllerUtils {

    public static Map<String, String> adaptUserAtrToWebAppStandard(String userName, int typeOfWork) {
        Map<String, String> userAtrMap = new HashMap<>();
        userAtrMap.put(USER_NAME, userName);
        userAtrMap.put(TYPE_OF_WORK_WITH_FILES, String.valueOf(typeOfWork));
        return userAtrMap;
    }

    public static String generateTypeOfWorkRadioButtons(String currentTypeOfWork) {
        StringBuilder sb = new StringBuilder();
        Map<Object, String> typeOfWorkMap = ConfigFile.typeOfWorkMap;
        for (Map.Entry<Object, String> entry : typeOfWorkMap.entrySet()) {
            String integerTypeOfWork = entry.getKey().toString();
            String friendlyStrTypeOfWork = entry.getValue();
            if(currentTypeOfWork.equals(integerTypeOfWork)){
                sb.append(radioButton(friendlyStrTypeOfWork, integerTypeOfWork, TYPE_OF_WORK_WITH_FILES, true));
            }
            else {
                sb.append(radioButton(friendlyStrTypeOfWork, integerTypeOfWork, TYPE_OF_WORK_WITH_FILES, false));
            }
        }
        return sb.toString();
    }
}
