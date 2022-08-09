package org.vshmaliukh.console_terminal_app.input_handler;

import org.vshmaliukh.services.input_services.ConstantsForItemInputValidation;

import java.io.PrintWriter;
import java.util.Scanner;

import static org.vshmaliukh.console_terminal_app.input_handler.ConstantsForConsoleUserInputHandler.*;

public class ConsoleInputHandlerForUser extends AbstractConsoleInputHandler {

    public ConsoleInputHandlerForUser(Scanner scanner, PrintWriter printWriter) {
        super(scanner, printWriter);
    }

    public int getTypeOfWorkWithFiles(){
        return getUserInteger(
                MESSAGE_ENTER_TYPE_OF_WORK_WITH_FILES,
                ConstantsForItemInputValidation.PATTERN_FOR_TYPE_OF_WORK_WITH_FILES);
    }

    public String getUserName(){
        return getUserString(
                MESSAGE_ENTER_USER_NAME,
                ConstantsForItemInputValidation.PATTERN_FOR_USER_NAME);
    }
}
