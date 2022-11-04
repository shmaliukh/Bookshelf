package com.vshmaliukh.httpclientmodule.console_http_client_app;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HttpClientApp implements CommandLineRunner {

    final HttpClientUI httpClientUI;

    public HttpClientApp(HttpClientUI httpClientUI) {
        this.httpClientUI = httpClientUI;
    }

    @Override
    public void run(String... args) {
        httpClientUI.startWork(true);
    }
}
