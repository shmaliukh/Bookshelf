package org.vshmaliukh.terminal.services.input_services;

import org.vshmaliukh.web.ScannerWrapper;

import java.io.PrintWriter;

import static org.vshmaliukh.terminal.services.input_services.ConstantsForUserInputHandler.*;

public class InputHandlerForUser extends InputHandler {

    public InputHandlerForUser(ScannerWrapper scanner, PrintWriter printWriter) {
        super(scanner, printWriter);
    }

    public int getTypeOfWorkWithFiles(){
        return getUserInteger(
                MESSAGE_ENTER_TYPE_OF_WORK_WITH_FILES,
                PATTERN_FOR_TYPE_OF_WORK_WITH_FILES);
    }

    public String getUserName(){
        return getUserString(
                MESSAGE_ENTER_USER_NAME,
                PATTERN_FOR_USER_NAME);
    }
}
