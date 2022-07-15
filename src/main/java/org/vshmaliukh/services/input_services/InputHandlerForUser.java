package org.vshmaliukh.services.input_services;

import java.io.PrintWriter;
import java.util.Scanner;

import static org.vshmaliukh.constants.ConstantsForUserInputHandler.*;

public class InputHandlerForUser extends UserInputHandler{

    public InputHandlerForUser(Scanner scanner, PrintWriter printWriter) {
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
