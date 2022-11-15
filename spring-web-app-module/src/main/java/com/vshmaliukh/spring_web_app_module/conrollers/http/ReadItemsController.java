package com.vshmaliukh.spring_web_app_module.conrollers.http;

import com.vshmaliukh.spring_web_app_module.SpringBootWebUtil;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.vshmaliukh.services.SaveReadShelfHandler;
import org.vshmaliukh.shelf.literature_items.Item;
import org.vshmaliukh.shelf.literature_items.ItemHandlerProvider;
import org.vshmaliukh.shelf.literature_items.book_item.Book;
import org.vshmaliukh.shelf.literature_items.comics_item.Comics;
import org.vshmaliukh.shelf.literature_items.magazine_item.Magazine;
import org.vshmaliukh.shelf.literature_items.newspaper_item.Newspaper;

import java.util.Collections;
import java.util.List;

@Slf4j
@RestController
public class ReadItemsController {

    public static final String READ_ITEMS_BY_TYPE = "/read_items_by_type";
    final SpringBootWebUtil springBootWebUtil;

    public ReadItemsController(SpringBootWebUtil springBootWebUtil) {
        this.springBootWebUtil = springBootWebUtil;
    }

    @GetMapping(READ_ITEMS_BY_TYPE)
    ResponseEntity<List<? extends Item>> readItemLisByClassTypeAsGsonStr(@CookieValue String userName,
                                                                         @CookieValue int typeOfWork,
                                                                         @RequestParam String itemClassType) {
        SaveReadShelfHandler shelfHandler = springBootWebUtil.generateSpringBootShelfHandler(userName, typeOfWork);
        Class<? extends Item> classByName = ItemHandlerProvider.getClassByName(itemClassType);
        if (classByName != null) {
            List<? extends Item> sortedItemsByClass = shelfHandler.getSortedItemsByClass(classByName);
            return ResponseEntity.ok(sortedItemsByClass);
        }
        log.warn("problem to find class type for '{}' // classByName == null", itemClassType);
        return ResponseEntity.badRequest().body(Collections.emptyList());
    }

//    @PostMapping(READ_ITEMS_BY_TYPE)
//    ResponseEntity<List<? extends Item>> postMappingReadItemLisByClassTypeAsGsonStr(@CookieValue String userName,
//                                                                                    @CookieValue int typeOfWork,
//                                                                                    @RequestBody String itemClassType) {
//        SaveReadShelfHandler shelfHandler = springBootWebUtil.generateSpringBootShelfHandler(userName, typeOfWork);
//        Class<? extends Item> classByName = ItemHandlerProvider.getClassByName(itemClassType);
//        if (classByName != null) {
//            List<? extends Item> sortedItemsByClass = shelfHandler.getSortedItemsByClass(classByName);
//            return ResponseEntity.ok(sortedItemsByClass);
//        }
//        log.warn("problem to find class type for '{}' // classByName == null", itemClassType);
//        return ResponseEntity.badRequest().body(Collections.emptyList());
//    }


    @PostMapping(READ_ITEMS_BY_TYPE)
    ResponseEntity<List<Item>> postMappingReadItemLisByClassTypeAsGsonStr(@CookieValue String userName,
                                                                          @CookieValue int typeOfWork,
                                                                          @RequestBody String itemClassType) {
        SaveReadShelfHandler shelfHandler = springBootWebUtil.generateSpringBootShelfHandler(userName, typeOfWork);
        Class<? extends Item> classByName = ItemHandlerProvider.getClassByName(itemClassType);
        if (classByName != null) {
            List sortedItemsByClass = shelfHandler.getSortedItemsByClass(classByName);
            return ResponseEntity.ok(sortedItemsByClass);
        }
        log.warn("problem to find class type for '{}' // classByName == null", itemClassType);
        return ResponseEntity.badRequest().body(Collections.emptyList());
    }


    @PostMapping(READ_ITEMS_BY_TYPE + "/Book")
    ResponseEntity<List<Book>> readListOfBooks(@CookieValue String userName,
                                               @CookieValue int typeOfWork) {
        return getResponseEntityWithItemsByClass(userName, typeOfWork, Book.class);
    }

    @PostMapping(READ_ITEMS_BY_TYPE + "/Magazine")
    ResponseEntity<List<Magazine>> readListOfMagazines(@CookieValue String userName,
                                                       @CookieValue int typeOfWork) {
        return getResponseEntityWithItemsByClass(userName, typeOfWork, Magazine.class);
    }

    @PostMapping(READ_ITEMS_BY_TYPE + "/Newspaper")
    ResponseEntity<List<Newspaper>> readListOfNewspapers(@CookieValue String userName,
                                                         @CookieValue int typeOfWork) {
        return getResponseEntityWithItemsByClass(userName, typeOfWork, Newspaper.class);
    }

    @PostMapping(READ_ITEMS_BY_TYPE + "/Comics")
    ResponseEntity<List<Comics>> readListOfComics(@CookieValue String userName,
                                                  @CookieValue int typeOfWork) {
        return getResponseEntityWithItemsByClass(userName, typeOfWork, Comics.class);
    }

    @NotNull
    private <T extends Item> ResponseEntity<List<T>> getResponseEntityWithItemsByClass(String userName, int typeOfWork, Class<T> classType) {
        SaveReadShelfHandler shelfHandler = springBootWebUtil.generateSpringBootShelfHandler(userName, typeOfWork);
        List<T> sortedItemsByClass = shelfHandler.getSortedItemsByClass(classType);
        return ResponseEntity.ok(sortedItemsByClass);
    }

}
