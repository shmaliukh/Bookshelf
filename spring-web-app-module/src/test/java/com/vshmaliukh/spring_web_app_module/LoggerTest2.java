package com.vshmaliukh.spring_web_app_module;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.slf4j.MDC;

@Slf4j
public class LoggerTest2 {

    @Test
    public void test() {
        MDC.put("serviceName", "myService");
        log.info("hello");
    }
}
