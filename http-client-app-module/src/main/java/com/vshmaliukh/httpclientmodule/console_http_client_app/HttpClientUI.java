package com.vshmaliukh.httpclientmodule.console_http_client_app;

import com.vshmaliukh.httpclientmodule.HttpClientAppConfig;
import com.vshmaliukh.httpclientmodule.http_client_services.apache_http_client_service.ConsoleApacheHttpShelfHandler;
import com.vshmaliukh.httpclientmodule.http_client_services.feign_client_service.ConsoleFeignShelfHandler;
import com.vshmaliukh.httpclientmodule.http_client_services.rest_template_client_service.ConsoleRestTemplateShelfHandler;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.vshmaliukh.console_terminal_app.ConsoleUI;
import org.vshmaliukh.services.input_handler.ConsoleInputHandlerForUser;

import javax.annotation.PostConstruct;
import java.io.PrintWriter;
import java.util.Scanner;

@Component
@NoArgsConstructor
public class HttpClientUI extends ConsoleUI {

    ConsoleFeignShelfHandler consoleFeignShelfHandler;

    protected int httpClientType;
    protected HttpClientInputHandlerForUser httpClientInputHandlerForUser;

    @Autowired
    public HttpClientUI(ConsoleFeignShelfHandler consoleFeignShelfHandler) {
        this.consoleFeignShelfHandler = consoleFeignShelfHandler;
    }

    @PostConstruct
    protected void setUpConsoleUI() {
        scanner = new Scanner(System.in);
        printWriter = new PrintWriter(System.out, true);
        consoleInputHandlerForUser = new ConsoleInputHandlerForUser(scanner, printWriter);
        httpClientInputHandlerForUser = new HttpClientInputHandlerForUser(scanner, printWriter);
    }

    @Override
    public void setUpTypeOfWorkWithFiles() {
        httpClientType = httpClientInputHandlerForUser.getTypeOfHttpClientWork();
        saveReadServiceType = httpClientInputHandlerForUser.getTypeOfWorkWithFiles();
        configShelfHandler();
    }

    @Override
    public void configShelfHandler() {
        String userName = user.getName();
        switch (httpClientType) {
            case HttpClientAppConfig.APACHE_HTTP_MODE_WORK:
                shelfHandler = new ConsoleApacheHttpShelfHandler(scanner, printWriter, userName, saveReadServiceType);
                break;
            case HttpClientAppConfig.REST_TEMPLATE_MODE_WORK:
                shelfHandler = new ConsoleRestTemplateShelfHandler(scanner, printWriter, userName, saveReadServiceType);
                break;
            case HttpClientAppConfig.FEIGN_MODE_WORK:
                consoleFeignShelfHandler.setScanner(scanner);
                consoleFeignShelfHandler.setPrintWriter(printWriter);
                consoleFeignShelfHandler.setUpDataService(userName, saveReadServiceType);
                shelfHandler = consoleFeignShelfHandler;
                break;
            default:
                httpClientType = HttpClientAppConfig.APACHE_HTTP_MODE_WORK;
                shelfHandler = new ConsoleApacheHttpShelfHandler(scanner, printWriter, userName, saveReadServiceType);
                break;
        }
        informAboutHttpClientTypeOfWork(httpClientType);
    }

    public void informAboutHttpClientTypeOfWork(int typeOfHttpClient) {
        printWriter.println("Http client type of work:");
        switch (typeOfHttpClient) {
            case HttpClientAppConfig.APACHE_HTTP_MODE_WORK:
                printWriter.println("APACHE_HTTP_MODE_WORK");
                break;
            case HttpClientAppConfig.REST_TEMPLATE_MODE_WORK:
                printWriter.println("REST_TEMPLATE_MODE_WORK");
                break;
            case HttpClientAppConfig.FEIGN_MODE_WORK:
                printWriter.println("FEIGN_MODE_WORK");
                break;
            default:
                printWriter.println("APACHE_HTTP_MODE_WORK");
                break;
        }
    }

}
