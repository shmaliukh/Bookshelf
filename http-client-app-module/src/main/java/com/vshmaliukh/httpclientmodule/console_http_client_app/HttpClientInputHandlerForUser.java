package com.vshmaliukh.httpclientmodule.console_http_client_app;

import org.vshmaliukh.services.input_handler.ConsoleInputHandlerForUser;
import org.vshmaliukh.services.input_services.ConstantsForItemInputValidation;

import java.io.PrintWriter;
import java.util.Scanner;

import static com.vshmaliukh.httpclientmodule.HttpClientAppConfig.MESSAGE_ENTER_TYPE_OF_WORK_WITH_HTTP_CLIENT;

public class HttpClientInputHandlerForUser extends ConsoleInputHandlerForUser {

    public HttpClientInputHandlerForUser(Scanner scanner, PrintWriter printWriter) {
        super(scanner, printWriter);
    }

    public int getTypeOfHttpClientWork(){
        return getUserInteger(
                MESSAGE_ENTER_TYPE_OF_WORK_WITH_HTTP_CLIENT,
                ConstantsForItemInputValidation.PATTERN_FOR_TYPE_OF_WORK_WITH_FILES);
    }

}
