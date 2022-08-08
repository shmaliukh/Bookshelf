package org.vshmaliukh.console_terminal.services.input_services;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

@Slf4j
public class WebInputHandler extends InputHandler {

    @Override
    public String getUserString(String inputStr, Pattern pattern) {
        if (isValidInputString(inputStr, pattern)) {
            return inputStr;
        }
        return null;
    }

    @Override
    public Integer getUserInteger(String inputInteger, Pattern pattern) {
        if (isValidInputInteger(inputInteger, pattern)) {
            try {
                return Integer.parseInt(inputInteger);
            } catch (NumberFormatException nfe) {
                log.error("[WebInputHandler] Problem to parse user integer input. Exception: ", nfe);
            }
        }
        return null;
    }

    @Override
    public Date getUserDate(String inputDate, SimpleDateFormat dateFormat) {
        if (isValidInputDate(inputDate, dateFormat)) {
            try {
                return dateFormat.parse(inputDate);
            } catch (ParseException pe) {
                log.error("[WebInputHandler] Problem to parse user date input. Exception: ", pe);
            }
        }
        return null;
    }

    @Override
    public Boolean getUserBoolean(String inputBoolean, Pattern pattern) {
        if (isValidInputString(inputBoolean, pattern)) {
            return inputBoolean.equals("y") || inputBoolean.equals("true");
        }
        return false;
    }
}
