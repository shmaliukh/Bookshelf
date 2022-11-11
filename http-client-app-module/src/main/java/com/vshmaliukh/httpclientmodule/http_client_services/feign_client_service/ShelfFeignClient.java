package com.vshmaliukh.httpclientmodule.http_client_services.feign_client_service;

import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.vshmaliukh.UserDataModelForJson;
import org.vshmaliukh.shelf.literature_items.Item;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static com.vshmaliukh.httpclientmodule.HttpClientAppConfig.READ_ITEMS_BY_TYPE_PAGE;
import static org.vshmaliukh.Constants.LOG_IN_TITLE;

@FeignClient(name = "${feign.name}", url = "${feign.url}")
public interface ShelfFeignClient {

    @RequestMapping(value = "/" + READ_ITEMS_BY_TYPE_PAGE, method = RequestMethod.GET)
    @Headers({ // todo remove
            "Content-Type: application/json",
            "Cookie: userName=Vlad",
            "Cookie: typeOfWork=4",
    })
    ResponseEntity<List<? extends Item>> readItemLisByClassTypeAsGsonStr(@CookieValue String userName,
                                                                         @CookieValue int typeOfWork,
                                                                         @RequestParam String itemClassType);

    @RequestMapping(value = "/" + LOG_IN_TITLE, method = RequestMethod.POST)
    String doPost(@RequestBody UserDataModelForJson userModel, // todo rename
                  HttpServletResponse response);


}
