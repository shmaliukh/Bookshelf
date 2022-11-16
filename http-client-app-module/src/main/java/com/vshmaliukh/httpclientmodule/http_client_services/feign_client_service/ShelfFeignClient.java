package com.vshmaliukh.httpclientmodule.http_client_services.feign_client_service;

import feign.Headers;
import feign.Param;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.vshmaliukh.UserDataModelForJson;
import org.vshmaliukh.shelf.literature_items.Item;

import java.util.List;

import static com.vshmaliukh.httpclientmodule.HttpClientAppConfig.READ_ITEMS_BY_TYPE_PAGE;
import static org.vshmaliukh.Constants.LOG_IN_TITLE;

@FeignClient(
        name = "${feign.name}",
        url = "${feign.url}",
        configuration = MyFeignClientConfig.class
)
public interface ShelfFeignClient {

    @PostMapping("/ping/" + LOG_IN_TITLE)
    @Headers({"Content-Type: application/json",})
    ResponseEntity logIn(@Param("userModel") UserDataModelForJson userModel);

    @GetMapping("/" + READ_ITEMS_BY_TYPE_PAGE)
    @Headers({"Content-Type: application/json",})
    ResponseEntity<List<? extends Item>> readItemLisByClassType(@CookieValue("userName") String userName,
                                                                @CookieValue("typeOfWork") int typeOfWork,
                                                                @Param("itemClassType") String itemClassType);
}
