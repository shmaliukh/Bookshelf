package org.vshmaliukh.services.input_services;

import java.io.PrintWriter;
import java.util.Date;
import java.util.Scanner;

import static org.vshmaliukh.ConstantsForTerminal.DATE_FORMAT;
import static org.vshmaliukh.services.input_services.ConstantsForUserInputHandler.*;

public class InputHandlerForLiterature extends InputHandler {

    public InputHandlerForLiterature(Scanner scanner, PrintWriter printWriter) {
        super(scanner, printWriter);
    }

    public String getUserLiteratureName() {
        return getUserString(
                MESSAGE_ENTER_LITERATURE_NAME,
                PATTERN_FOR_NAME);
    }

    public String getUserLiteratureAuthor() {
        return getUserString(
                MESSAGE_ENTER_LITERATURE_AUTHOR,
                PATTERN_FOR_AUTHOR);
    }

    public boolean getUserLiteratureIsBorrowed() {
        return getUserBoolean(
                MESSAGE_ENTER_LITERATURE_IS_BORROWED,
                PATTERN_FOR_IS_BORROWED);
    }

    public int getUserLiteraturePages() {
        return getUserInteger(
                MESSAGE_ENTER_LITERATURE_PAGES_NUMBER,
                PATTERN_FOR_PAGES);
    }

    public Date getUserLiteratureDateOfIssue() {
        return getUserDate(
                MESSAGE_ENTER_LITERATURE_DATE,
                DATE_FORMAT);
    }
}
