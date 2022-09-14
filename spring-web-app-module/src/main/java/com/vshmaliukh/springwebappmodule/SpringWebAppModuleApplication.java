package com.vshmaliukh.springwebappmodule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringWebAppModuleApplication {

    public static final String BASE_PAGE_WITH_PLACEHOLDER = "base_page_with_placeholder";
    public static final String GENERATED_HTML_STR = "generatedHtmlStr";

    public static final String CHOOSE_TYPE_OF_WORK_TITLE = "choose_type_of_work";

    public static void main(String[] args) {
        SpringApplication.run(SpringWebAppModuleApplication.class, args);
    }

}