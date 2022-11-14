package com.vshmaliukh.httpclientmodule.http_client_services.feign_client_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.vshmaliukh.UserDataModelForJson;
import org.vshmaliukh.shelf.literature_items.Item;

import java.util.List;

import static com.vshmaliukh.httpclientmodule.HttpClientAppConfig.READ_ITEMS_BY_TYPE_PAGE;
import static org.vshmaliukh.Constants.LOG_IN_TITLE;

@RestController
public final class ShelfFeignClientController implements ShelfFeignClient {

    final ShelfFeignClient feignClientImp;

    @Autowired
    public ShelfFeignClientController(ShelfFeignClient feignClientImp) {
        this.feignClientImp = feignClientImp;
    }

    @Override
    @GetMapping("/" + READ_ITEMS_BY_TYPE_PAGE)
    public ResponseEntity<List<? extends Item>> readItemLisByClassTypeAsGsonStr(String userName, int typeOfWork, String itemClassType) {
        return feignClientImp.readItemLisByClassTypeAsGsonStr(userName, typeOfWork, itemClassType);
    }

    @Override
    @PostMapping("/ping/" + LOG_IN_TITLE)
    public ResponseEntity logIn(UserDataModelForJson userModel) {
        return feignClientImp.logIn(userModel);
    }

}
