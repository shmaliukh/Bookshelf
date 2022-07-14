package org.vshmaliukh.services;

import java.io.PrintWriter;
import java.text.ParseException;
import java.util.Date;
import java.util.Scanner;

import static org.vshmaliukh.constants.ConstantsForTerminal.DATE_FORMAT;
import static org.vshmaliukh.constants.ConstantsForUserInputHandler.*;

public class InputHandlerForLiterature extends UserInputHandler{

    public InputHandlerForLiterature(Scanner scanner, PrintWriter printWriter) {
        super(scanner, printWriter);
    }

    public String getUserName(){
        return getUserString(
                MESSAGE_ENTER_USER_NAME,
                PATTERN_FOR_USER_NAME);
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

    public Date getUserDateOfIssue() throws ParseException {
        return getUserDate(
                MESSAGE_ENTER_LITERATURE_DATE,
                DATE_FORMAT);
    }
}
