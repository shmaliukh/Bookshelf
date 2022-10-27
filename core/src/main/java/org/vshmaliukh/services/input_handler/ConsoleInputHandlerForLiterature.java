package org.vshmaliukh.services.input_handler;

import org.vshmaliukh.BaseAppConfig;
import org.vshmaliukh.services.input_services.ConstantsForItemInputValidation;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class ConsoleInputHandlerForLiterature extends AbstractConsoleInputHandler {

    public ConsoleInputHandlerForLiterature(Scanner scanner, PrintWriter printWriter) {
        super(scanner, printWriter);
    }

    public String getUserLiteratureName() {
        return getUserString(
                ConstantsForConsoleUserInputHandler.MESSAGE_ENTER_LITERATURE_NAME,
                ConstantsForItemInputValidation.PATTERN_FOR_NAME);
    }

    public String getUserLiteraturePublisher() {
        return getUserString(
                ConstantsForConsoleUserInputHandler.MESSAGE_ENTER_LITERATURE_PUBLISHER,
                ConstantsForItemInputValidation.PATTERN_FOR_PUBLISHER);
    }

    public String getUserLiteratureAuthor() {
        return getUserString(
                ConstantsForConsoleUserInputHandler.MESSAGE_ENTER_LITERATURE_AUTHOR,
                ConstantsForItemInputValidation.PATTERN_FOR_AUTHOR);
    }

    public boolean getUserLiteratureIsBorrowed() {
        return getUserBoolean(
                ConstantsForConsoleUserInputHandler.MESSAGE_ENTER_LITERATURE_IS_BORROWED,
                ConstantsForItemInputValidation.PATTERN_FOR_IS_BORROWED);
    }

    public int getUserLiteraturePages() {
        return getUserInteger(
                ConstantsForConsoleUserInputHandler.MESSAGE_ENTER_LITERATURE_PAGES_NUMBER,
                ConstantsForItemInputValidation.PATTERN_FOR_PAGES);
    }

    public Date getUserLiteratureDateOfIssue() {
        return getUserDate(
                ConstantsForConsoleUserInputHandler.MESSAGE_ENTER_LITERATURE_DATE,
                new SimpleDateFormat(BaseAppConfig.DATE_FORMAT_STR));
    }
}
