package com.vshmaliukh.spring_web_app_module.conrollers.http;

import com.vshmaliukh.spring_web_app_module.SpringBootWebUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.vshmaliukh.services.SaveReadShelfHandler;
import org.vshmaliukh.shelf.literature_items.Item;
import org.vshmaliukh.shelf.literature_items.ItemHandlerProvider;

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

    @PostMapping(READ_ITEMS_BY_TYPE)
    ResponseEntity<List<? extends Item>> postMappingReadItemLisByClassTypeAsGsonStr(@CookieValue String userName,
                                                                                    @CookieValue int typeOfWork,
                                                                                    @RequestBody String itemClassType) {
        SaveReadShelfHandler shelfHandler = springBootWebUtil.generateSpringBootShelfHandler(userName, typeOfWork);
        Class<? extends Item> classByName = ItemHandlerProvider.getClassByName(itemClassType);
        if (classByName != null) {
            List<? extends Item> sortedItemsByClass = shelfHandler.getSortedItemsByClass(classByName);
            return ResponseEntity.ok(sortedItemsByClass);
        }
        log.warn("problem to find class type for '{}' // classByName == null", itemClassType);
        return ResponseEntity.badRequest().body(Collections.emptyList());
    }

}
