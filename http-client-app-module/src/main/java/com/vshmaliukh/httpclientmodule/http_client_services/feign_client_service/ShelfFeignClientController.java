package com.vshmaliukh.httpclientmodule.http_client_services.feign_client_service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.vshmaliukh.UserDataModelForJson;
import org.vshmaliukh.shelf.literature_items.Item;

import java.util.List;

import static com.vshmaliukh.httpclientmodule.HttpClientAppConfig.READ_ITEMS_BY_TYPE_PAGE;
import static org.vshmaliukh.Constants.LOG_IN_TITLE;

@RestController
public final class ShelfFeignClientController implements ShelfFeignClient{

    final ShelfFeignClient feignClientImp;

    public ShelfFeignClientController(ShelfFeignClient feignClientImp) {
        this.feignClientImp = feignClientImp;
    }

    @RequestMapping(value = "/" + READ_ITEMS_BY_TYPE_PAGE, method = RequestMethod.GET)
    public ResponseEntity<List<? extends Item>> readItemLisByClassTypeAsGsonStr(String userName, int typeOfWork, String itemClassType) {
        return feignClientImp.readItemLisByClassTypeAsGsonStr(userName, typeOfWork, itemClassType);
    }

    @Override
    @RequestMapping(value = "/" + LOG_IN_TITLE, method = RequestMethod.POST)
    public String doPost(UserDataModelForJson userModel
//            , HttpServletResponse response
    ) {
        return feignClientImp.doPost(userModel
//                , response
        );
    }

//    @RequestMapping(value = "/" + LOG_IN_TITLE, method = RequestMethod.POST)
//    String doPost(String userName, int typeOfWork, HttpServletResponse response) {
//        UserDataModelForJson userDataModelForJson = new UserDataModelForJson(userName, typeOfWork);
//        return
////                "";
//                feignClientImp.doPost(userDataModelForJson, response);
//    }

}
