package com.vshmaliukh.httpclientmodule.http_client_services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Slf4j
public final class MyHttpClientUtils {

    private MyHttpClientUtils(){}

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

}
