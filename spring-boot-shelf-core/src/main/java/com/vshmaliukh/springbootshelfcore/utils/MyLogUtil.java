package com.vshmaliukh.springbootshelfcore.utils;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

@Slf4j
public final class MyLogUtil {

    private MyLogUtil() {
    }

    public static void logInfo(Object service, String infoMessage) {
        logInfo(service.getClass(), infoMessage);
    }

    public static void logInfo(Class serviceClassType, String infoMessage) {
        log.info(formBaseInfoMessage(serviceClassType.getSimpleName(), infoMessage));
    }

    public static void logInfo(String serviceName, String infoMessage) {
        log.info(formBaseInfoMessage(serviceName, infoMessage));
    }

    public static void logInfo(String serviceName, String infoMessage, String additionInfo) {
        log.info(formBaseInfoMessage(serviceName, infoMessage) + " // " + additionInfo);
    }

    public static void logInfo(Object serviceName, String infoMessage, String additionInfo) {
        log.info(formBaseInfoMessage(serviceName, infoMessage) + " // " + additionInfo);
    }

    @NotNull
    private static String formBaseInfoMessage(String serviceName, String infoMessage) {
        return "[" + serviceName + "] info: " + infoMessage;
    }

    private static String formBaseInfoMessage(Object serviceName, String infoMessage) {
        return "[" + serviceName.getClass().getSimpleName() + "] info: " + infoMessage;
    }


}
