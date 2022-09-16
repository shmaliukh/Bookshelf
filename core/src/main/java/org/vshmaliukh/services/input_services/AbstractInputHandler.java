package org.vshmaliukh.services.input_services;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

@Slf4j
public abstract class AbstractInputHandler {

    public static boolean isValidInputString(String inputStr, Pattern pattern) {
        if (inputStr == null) {
            return false;
        }
        return isMatcher(inputStr, pattern);
    }

    public static boolean isValidInputInteger(String inputStr, Pattern pattern) {
        if (inputStr == null || inputStr.length() > 8 || inputStr.length() == 0) {
            return false;
        }
        return isMatcher(inputStr, pattern);
    }

    public static boolean isValidInputDate(String inputStr, SimpleDateFormat dateFormat) {
        if (inputStr == null) {
            return false;
        }
        try {
            dateFormat.setLenient(false);
            dateFormat.parse(inputStr);
            dateFormat.parse(inputStr);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public static boolean isMatcher(String inputStr, Pattern pattern) {
        return pattern.matcher(inputStr).matches();
    }

    protected abstract String getUserString(String message, Pattern pattern);

    protected abstract Integer getUserInteger(String message, Pattern pattern);

    protected abstract Date getUserDate(String message, SimpleDateFormat dateFormat);

    protected abstract Boolean getUserBoolean(String message, Pattern pattern);
}
