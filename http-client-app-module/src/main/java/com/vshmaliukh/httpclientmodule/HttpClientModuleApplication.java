package com.vshmaliukh.httpclientmodule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
        "com.vshmaliukh.httpclientmodule.console_http_client_app",
})
public class HttpClientModuleApplication {

    public static void main(String[] args) {
        SpringApplication.run(HttpClientModuleApplication.class, args);
    }

}
