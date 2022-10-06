package com.vshmaliukh.springwebappmodule.shelf;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class DbEntityService {

    protected static void logInfo(String serviceName, String message) {
        log.info("[" + serviceName + "Service] info: " + message);
    }

    protected static <T> void logInfo(T entity, String serviceName, String message) {
        logInfo(serviceName, "'" + entity + "' " + message);
    }

    protected static void logError(String serviceName, Exception e) {
        log.error("[" + serviceName + "] error: " + e.getMessage(), e);
    }

}
