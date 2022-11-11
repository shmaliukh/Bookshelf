package com.vshmaliukh.httpclientmodule.http_client_services.feign_client_service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.vshmaliukh.UserDataModelForJson;
import org.vshmaliukh.shelf.literature_items.Item;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static com.vshmaliukh.httpclientmodule.HttpClientAppConfig.READ_ITEMS_BY_TYPE_PAGE;
import static org.vshmaliukh.Constants.LOG_IN_TITLE;

@RestController
public final class ShelfFeignClientController {

    final ShelfFeignClient feignClientImp;

    public ShelfFeignClientController(ShelfFeignClient feignClientImp) {
        this.feignClientImp = feignClientImp;
    }

    @RequestMapping(value = "/" + READ_ITEMS_BY_TYPE_PAGE, method = RequestMethod.GET)
    ResponseEntity<List<? extends Item>> readItemLisByClassTypeAsGsonStr(String userName, int typeOfWork, String itemClassType) {
        return feignClientImp.readItemLisByClassTypeAsGsonStr(userName, typeOfWork, itemClassType);
    }

    @RequestMapping(value = "/" + LOG_IN_TITLE, method = RequestMethod.POST)
    String logIn(String userName, int typeOfWork, HttpServletResponse response) {
        UserDataModelForJson userDataModelForJson = new UserDataModelForJson(userName, typeOfWork);
//        response.getHeaders()
        return feignClientImp.doPost(userDataModelForJson, response);
    }


}
