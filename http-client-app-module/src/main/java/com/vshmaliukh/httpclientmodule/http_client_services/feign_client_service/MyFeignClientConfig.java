package com.vshmaliukh.httpclientmodule.http_client_services.feign_client_service;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;

import java.util.List;

import static com.vshmaliukh.httpclientmodule.http_client_services.feign_client_service.FeignClientServiceImp.cookieHeaders;

public final class MyFeignClientConfig {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            List<String> cookieHeadersValuesAsList = cookieHeaders.getValuesAsList(HttpHeaders.COOKIE);
            requestTemplate.header(HttpHeaders.COOKIE, cookieHeadersValuesAsList);
        };
    }

    //todo
//    Use OkHttpClient instead of the default one to support HTTP/2
//    <dependency>
//        <groupId>io.github.openfeign</groupId>
//        <artifactId>feign-okhttp</artifactId>
//    </dependency>
//    @Bean
//    public OkHttpClient client() {
//        return new OkHttpClient();
//    }

}
