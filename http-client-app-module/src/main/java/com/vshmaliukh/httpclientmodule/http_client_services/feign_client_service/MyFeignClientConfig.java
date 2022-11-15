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

//    @Bean
//    public Decoder feignDecoder() {
////        HttpMessageConverter jacksonConverter = new MappingJackson2HttpMessageConverter(customObjectMapper());
////
////        HttpMessageConverters httpMessageConverters = new HttpMessageConverters(jacksonConverter);
////        ObjectFactory<HttpMessageConverters> objectFactory = () -> httpMessageConverters;
////
////        return new ResponseEntityDecoder(new SpringDecoder(objectFactory));
//        return null;
//    }

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
