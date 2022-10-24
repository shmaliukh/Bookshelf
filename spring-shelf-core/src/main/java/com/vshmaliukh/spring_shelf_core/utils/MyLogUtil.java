package com.vshmaliukh.spring_shelf_core.utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class MyLogUtil {

    private MyLogUtil() {
    }

    public static void logDebug(String serviceName, String message, Object... values) {
        log.debug("[" + serviceName + "] debug:" + message, values);
    }

    public static void logDebug(Class serviceClassType, String message, Object... values) {
        logDebug(serviceClassType.getSimpleName(), message, values);
    }

    public static void logDebug(Object serviceInstance, String message, Object... values) {
        logDebug(serviceInstance.getClass(), message, values);
    }

    public static void logWarn(String serviceName, String message, Object... values) {
        log.warn("[" + serviceName + "] warn:" + message, values);
    }

    public static void logWarn(Class serviceClassType, String message, Object... values) {
        logDebug(serviceClassType.getSimpleName(), message, values);
    }

    public static void logWarn(Object serviceInstance, String message, Object... values) {
        logDebug(serviceInstance.getClass(), message, values);
    }

    public static void logErr(String serviceName, String message, Object... values) {
        log.error("[" + serviceName + "] error:" + message, values);
    }

    public static void logErr(Class serviceClassType, String message, Object... values) {
        logDebug(serviceClassType.getSimpleName(), message, values);
    }

    public static void logErr(Object serviceInstance, String message, Object... values) {
        logDebug(serviceInstance.getClass(), message, values);
    }

    public static void logInfo(String serviceName, String message, Object... values) {
        log.info("[" + serviceName + "] info:" + message, values);
    }

    public static void logInfo(Class serviceClassType, String message, Object... values) {
        logInfo(serviceClassType.getSimpleName(), message, values);
    }

    public static void logInfo(Object serviceInstance, String message, Object... values) {
        logInfo(serviceInstance.getClass(), message, values);
    }

    public static void logTrace(Object serviceInstance, String message, Object... values) {
        log.trace("[" + serviceInstance.getClass().getSimpleName() + "] trace:" + message, values);
    }

    // TODO refactor

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

    private static String formBaseInfoMessage(String serviceName, String infoMessage) {
        return "[" + serviceName + "] info: " + infoMessage;
    }

    private static String formBaseInfoMessage(Object serviceName, String infoMessage) {
        return "[" + serviceName.getClass().getSimpleName() + "] info: " + infoMessage;
    }

}
