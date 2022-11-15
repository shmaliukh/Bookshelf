package com.vshmaliukh.httpclientmodule.http_client_services.feign_client_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.vshmaliukh.UserDataModelForJson;
import org.vshmaliukh.shelf.literature_items.Item;
import org.vshmaliukh.shelf.literature_items.book_item.Book;
import org.vshmaliukh.shelf.literature_items.comics_item.Comics;
import org.vshmaliukh.shelf.literature_items.magazine_item.Magazine;
import org.vshmaliukh.shelf.literature_items.newspaper_item.Newspaper;

import java.util.Collections;
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

    public List<? extends Item> readItemLisByClassType(String userName, int typeOfWork, String itemClassType) {
        switch (itemClassType) {
            case "Book":
                return readListOfBooks(userName, typeOfWork).getBody();
            case "Magazine":
                return readListOfMagazines(userName, typeOfWork).getBody();
            case "Comics":
                return readListOfComics(userName, typeOfWork).getBody();
            case "Newspaper":
                return readListOfNewspapers(userName, typeOfWork).getBody();
            default:
                return Collections.emptyList();
        }
//        return shelfFeignClient.readItemLisByClassType(userName, typeOfWork, itemClassType);
    }

    @PostMapping("/" + READ_ITEMS_BY_TYPE_PAGE + "/Book")
    public ResponseEntity<List<Book>> readListOfBooks(String userName, int typeOfWork) {
        return shelfFeignClient.readListOfBooks(userName, typeOfWork);
    }

    @PostMapping("/" + READ_ITEMS_BY_TYPE_PAGE + "/Magazine")
    public ResponseEntity<List<Magazine>> readListOfMagazines(String userName, int typeOfWork) {
        return shelfFeignClient.readListOfMagazines(userName, typeOfWork);
    }

    @PostMapping("/" + READ_ITEMS_BY_TYPE_PAGE + "/Newspaper")
    public ResponseEntity<List<Newspaper>> readListOfNewspapers(String userName, int typeOfWork) {
        return shelfFeignClient.readListOfNewspapers(userName, typeOfWork);
    }

    @PostMapping("/" + READ_ITEMS_BY_TYPE_PAGE + "/Comics")
    public ResponseEntity<List<Comics>> readListOfComics(String userName, int typeOfWork) {
        return shelfFeignClient.readListOfComics(userName, typeOfWork);
    }

    @Override
    @PostMapping("/ping/" + LOG_IN_TITLE)
    public ResponseEntity logIn(UserDataModelForJson userModel) {
        return shelfFeignClient.logIn(userModel);
    }

}
