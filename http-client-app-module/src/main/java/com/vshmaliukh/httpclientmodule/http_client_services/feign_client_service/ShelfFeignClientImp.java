package com.vshmaliukh.httpclientmodule.http_client_services.feign_client_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.vshmaliukh.UserDataModelForJson;
import org.vshmaliukh.shelf.literature_items.Item;

import java.util.List;

import static com.vshmaliukh.httpclientmodule.HttpClientAppConfig.READ_ITEMS_BY_TYPE_PAGE;
import static org.vshmaliukh.Constants.LOG_IN_TITLE;

@Component
public final class ShelfFeignClientImp implements ShelfFeignClient {

    final ShelfFeignClient shelfFeignClient;

    @Autowired
    public ShelfFeignClientImp(ShelfFeignClient shelfFeignClient) {
        this.shelfFeignClient = shelfFeignClient;
    }

    @Override
    @PostMapping("/ping/" + LOG_IN_TITLE)
    public ResponseEntity logIn(UserDataModelForJson userModel) {
        return shelfFeignClient.logIn(userModel);
    }

    @GetMapping("/" + READ_ITEMS_BY_TYPE_PAGE)
    public ResponseEntity<List<? extends Item>> readItemLisByClassType(String userName, int typeOfWork, String itemClassType) {
        return shelfFeignClient.readItemLisByClassType(userName, typeOfWork, itemClassType);
    }

}
