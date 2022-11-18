package com.vshmaliukh.httpclientmodule.http_client_services.feign_client_service;

import com.vshmaliukh.httpclientmodule.AbstractSpringClientService;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.vshmaliukh.UserDataModelForJson;

import java.util.List;

@Slf4j
@Service
@NoArgsConstructor
public class FeignClientServiceImp extends AbstractSpringClientService {

    public static HttpHeaders cookieHeaders = new HttpHeaders(); // todo refactor

    @Autowired
    public FeignClientServiceImp(ShelfFeignClientImp shelfFeignClientController) {
        this.shelfFeignClientController = shelfFeignClientController;
    }

    @Override
    public void logIn(String userName, int typeOfWork) {
        this.userName = userName;
        this.typeOfWork = typeOfWork;

        UserDataModelForJson userDataModelForJson = new UserDataModelForJson(userName, typeOfWork);

        ResponseEntity<Void> responseEntity = shelfFeignClientController.logIn(userDataModelForJson);
        HttpHeaders responseHeaders = responseEntity.getHeaders();
        List<String> cookieStrList = responseHeaders.get(HttpHeaders.SET_COOKIE);
        if (cookieStrList != null) {
            FeignClientServiceImp.cookieHeaders.addAll(HttpHeaders.COOKIE, cookieStrList);
        } else {
            log.warn("problem to set up 'log_in' cookie values // Set-Cookie list == null");
        }
    }

}
