package com.vshmaliukh.httpclientmodule.http_client_services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.vshmaliukh.MyUtils;

import static org.vshmaliukh.Constants.TYPE_OF_WORK_WITH_SAVE_READ_SERVICE;
import static org.vshmaliukh.Constants.USER_NAME;

@Slf4j
public final class MyHttpClientUtils {

    public static final String SUCCESSFUL_LOG_IN_MESSAGE_STR = "successful authorized";
    public static final String PROBLEM_TO_LOG_IN_MESSAGE_STR = "problem to authorize";
    public static final String SUCCESSFUL_DELETED_ITEM_MESSAGE_STR = "successful deleted '{}' item";
    public static final String PROBLEM_TO_DELETE_ITEM_MESSAGE_STR = "problem to delete '{}' item";
    public static final String SUCCESSFUL_CHANGED_BORROWED_STATE_FOR_ITEM_MESSAGE_STR = "successful changed borrowed state for '{}' item";
    public static final String PROBLEM_TO_CHANGE_BORROWED_STATE_FOR_ITEM_MESSAGE_STR = "problem to change borrowed state for '{}' item";
    public static final String SUCCESSFUL_ADDED_ITEM_MESSAGE_STR = "successful added '{}' item";
    public static final String PROBLEM_TO_ADD_ITEM_TO_DB_MESSAGE_STR = "problem to add '{}' item to db";

    private MyHttpClientUtils() {
    }

    public static void informAboutResponseStatus(ResponseEntity<Void> responseEntity,
                                                 String messageIfSuccessStr,
                                                 String messageIfProblemStr,
                                                 Object... values) {
        HttpStatus statusCode = responseEntity.getStatusCode();
        if (statusCode.is2xxSuccessful()) {
            log.info("userName: '{}' "
                            + "// typeOfWork: '{}' // "
                            + messageIfSuccessStr,
                    values);
        } else {
            log.warn("userName: '{}' "
                            + "// typeOfWork: '{}' // "
                            + messageIfProblemStr
                            + " // response status code: '{}'",
                    values, statusCode);
        }
    }

    public static HttpHeaders generateAuthorizationCookieHeaders(String userName, int typeOfWork) {
        final HttpHeaders cookieHeaders = new HttpHeaders();
        cookieHeaders.add(HttpHeaders.COOKIE, MyUtils.generateCookieValue(USER_NAME, userName));
        cookieHeaders.add(HttpHeaders.COOKIE, MyUtils.generateCookieValue(TYPE_OF_WORK_WITH_SAVE_READ_SERVICE, typeOfWork));
        return cookieHeaders;
    }

}
