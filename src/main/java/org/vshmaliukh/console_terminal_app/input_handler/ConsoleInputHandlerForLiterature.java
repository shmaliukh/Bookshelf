package org.vshmaliukh.console_terminal_app.input_handler;

import org.vshmaliukh.services.input_services.ConstantsForItemInputValidation;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import static org.vshmaliukh.console_terminal_app.ConsoleGsonShelfHandler.DATE_FORMAT_STR;
import static org.vshmaliukh.console_terminal_app.input_handler.ConstantsForConsoleUserInputHandler.*;

public class ConsoleInputHandlerForLiterature extends AbstractConsoleInputHandler {

    public ConsoleInputHandlerForLiterature(Scanner scanner, PrintWriter printWriter) {
        super(scanner, printWriter);
    }

    public String getUserLiteratureName() {
        return getUserString(
                MESSAGE_ENTER_LITERATURE_NAME,
                ConstantsForItemInputValidation.PATTERN_FOR_NAME);
    }

    public String getUserLiteraturePublisher() {
        return getUserString(
                MESSAGE_ENTER_LITERATURE_PUBLISHER,
                ConstantsForItemInputValidation.PATTERN_FOR_PUBLISHER);
    }

    public String getUserLiteratureAuthor() {
        return getUserString(
                MESSAGE_ENTER_LITERATURE_AUTHOR,
                ConstantsForItemInputValidation.PATTERN_FOR_AUTHOR);
    }

    public boolean getUserLiteratureIsBorrowed() {
        return getUserBoolean(
                MESSAGE_ENTER_LITERATURE_IS_BORROWED,
                ConstantsForItemInputValidation.PATTERN_FOR_IS_BORROWED);
    }

    public int getUserLiteraturePages() {
        return getUserInteger(
                MESSAGE_ENTER_LITERATURE_PAGES_NUMBER,
                ConstantsForItemInputValidation.PATTERN_FOR_PAGES);
    }

    public Date getUserLiteratureDateOfIssue() {
        return getUserDate(
                MESSAGE_ENTER_LITERATURE_DATE,
                new SimpleDateFormat(DATE_FORMAT_STR));
    }
}
