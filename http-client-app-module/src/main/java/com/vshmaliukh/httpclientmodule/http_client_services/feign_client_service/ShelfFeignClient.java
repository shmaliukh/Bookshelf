package com.vshmaliukh.httpclientmodule.http_client_services.feign_client_service;

import com.vshmaliukh.httpclientmodule.http_client_services.BaseShelfHttpClient;
import feign.Param;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.vshmaliukh.UserDataModelForJson;
import org.vshmaliukh.shelf.literature_items.Item;

import java.util.List;

import static com.vshmaliukh.httpclientmodule.HttpClientAppConfig.*;

@FeignClient(
        name = "${feign.name}",
        url = "${feign.url}",
        configuration = MyFeignClientConfig.class
)
public interface ShelfFeignClient extends BaseShelfHttpClient {

    @PostMapping("/" + LOG_IN_VIA_USER_MODEL_PAGE)
    ResponseEntity<Void> logIn(@Param("userModel") UserDataModelForJson userModel);

    @PostMapping("/" + READ_ITEMS_BY_TYPE_PAGE)
    ResponseEntity<List<? extends Item>> readItemListByClassType(@CookieValue("userName") String userName,
                                                                 @CookieValue("typeOfWork") int typeOfWork,
                                                                 @Param("itemClassType") String itemClassType);

    @PutMapping("/" + PUT_ITEM_TO_DB_PAGE)
    <T extends Item> ResponseEntity<Void> saveItemAndGetResponse(@CookieValue("userName") String userName,
                                                                 @CookieValue("typeOfWork") int typeOfWork,
                                                                 @Param("item") T item);

    @PostMapping("/" + CHANGE_ITEM_BORROWED_STATE_PAGE)
    ResponseEntity<Void> changeItemBorrowedStateAndGetResponse(@CookieValue("userName") String userName,
                                                               @CookieValue("typeOfWork") int typeOfWork,
                                                               @Param("itemIndex") int itemIndex);

    @DeleteMapping("/" + DELETE_ITEM_PAGE)
    ResponseEntity<Void> deleteItemAndGetResponse(@CookieValue("userName") String userName,
                                                  @CookieValue("typeOfWork") int typeOfWork,
                                                  @Param("indexOfItem") int indexOfItem);

}
