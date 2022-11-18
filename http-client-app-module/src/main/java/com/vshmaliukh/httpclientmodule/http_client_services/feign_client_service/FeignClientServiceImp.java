package com.vshmaliukh.httpclientmodule.http_client_services.feign_client_service;

import com.vshmaliukh.httpclientmodule.http_client_services.AbstractSpringClientService;
import com.vshmaliukh.httpclientmodule.http_client_services.MyHttpClientUtils;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.vshmaliukh.UserDataModelForJson;

import java.util.List;

import static com.vshmaliukh.httpclientmodule.http_client_services.MyHttpClientUtils.informAboutResponseStatus;

@Slf4j
@Service
@NoArgsConstructor
public class FeignClientServiceImp extends AbstractSpringClientService {

    public static HttpHeaders cookieHeaders = new HttpHeaders(); // todo refactor

    @Autowired
    public FeignClientServiceImp(ShelfFeignClientImp shelfFeignClientController) {
        this.shelfClientImp = shelfFeignClientController;
    }

    @Override
    public void logIn(String userName, int typeOfWork) {
        init(userName, typeOfWork);
        UserDataModelForJson userDataModelForJson = new UserDataModelForJson(userName, typeOfWork);
        ResponseEntity<Void> responseEntity = shelfClientImp.logIn(userDataModelForJson);
        informAboutResponseStatus(
                responseEntity,
                MyHttpClientUtils.SUCCESSFUL_LOG_IN_MESSAGE_STR,
                MyHttpClientUtils.PROBLEM_TO_DELETE_ITEM_MESSAGE_STR,
                userName, typeOfWork);

        HttpHeaders responseHeaders = responseEntity.getHeaders();
        List<String> cookieStrList = responseHeaders.get(HttpHeaders.SET_COOKIE);
        if (cookieStrList != null) {
            FeignClientServiceImp.cookieHeaders.addAll(HttpHeaders.COOKIE, cookieStrList);
        } else {
            log.warn("problem to set up 'log_in' cookie values // Set-Cookie list == null");
        }
    }

}
