package com.vshmaliukh.httpclientmodule.http_client_services.feign_client_service;

import feign.Headers;
import feign.Param;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.vshmaliukh.UserDataModelForJson;
import org.vshmaliukh.shelf.literature_items.book_item.Book;
import org.vshmaliukh.shelf.literature_items.comics_item.Comics;
import org.vshmaliukh.shelf.literature_items.magazine_item.Magazine;
import org.vshmaliukh.shelf.literature_items.newspaper_item.Newspaper;

import java.util.List;

import static com.vshmaliukh.httpclientmodule.HttpClientAppConfig.READ_ITEMS_BY_TYPE_PAGE;
import static org.vshmaliukh.Constants.LOG_IN_TITLE;

@FeignClient(
        name = "${feign.name}",
        url = "${feign.url}",
        configuration = MyFeignClientConfig.class
)
public interface ShelfFeignClient {

    //    @PostMapping("/" + READ_ITEMS_BY_TYPE_PAGE)
//    @Headers({"Content-Type: application/json",
//    })
//    List<? extends Item> readItemLisByClassType(String userName,
//                                                int typeOfWork,
//                                                String itemClassType);

    @PostMapping("/" + READ_ITEMS_BY_TYPE_PAGE + "/Book")
    ResponseEntity<List<Book>> readListOfBooks(@CookieValue("userName") String userName,
                                               @CookieValue("typeOfWork") int typeOfWork);

    @PostMapping("/" + READ_ITEMS_BY_TYPE_PAGE + "/Magazine")
    ResponseEntity<List<Magazine>> readListOfMagazines(@CookieValue("userName") String userName,
                                                       @CookieValue("typeOfWork") int typeOfWork);

    @PostMapping("/" + READ_ITEMS_BY_TYPE_PAGE + "/Newspaper")
    ResponseEntity<List<Newspaper>> readListOfNewspapers(@CookieValue("userName") String userName,
                                                         @CookieValue("typeOfWork") int typeOfWork);

    @PostMapping("/" + READ_ITEMS_BY_TYPE_PAGE + "/Comics")
    ResponseEntity<List<Comics>> readListOfComics(@CookieValue("userName") String userName,
                                                  @CookieValue("typeOfWork") int typeOfWork);

    @PostMapping("/ping/" + LOG_IN_TITLE)
    @Headers({"Content-Type: application/json",})
    ResponseEntity logIn(@Param("userModel") UserDataModelForJson userModel);

}
