package com.vshmaliukh.spring_web_app_module.conrollers;

import com.vshmaliukh.spring_web_app_module.SpringBootWebUtil;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.vshmaliukh.services.SaveReadShelfHandler;
import org.vshmaliukh.shelf.literature_items.Item;
import org.vshmaliukh.shelf.literature_items.ItemHandlerProvider;

import java.util.Collections;
import java.util.List;

import static com.vshmaliukh.spring_web_app_module.conrollers.ReadItemsController.READ_ITEMS_BY_TYPE;

@Slf4j
@RestController
@RequestMapping("/" + READ_ITEMS_BY_TYPE)
public class ReadItemsController {

    public static final String READ_ITEMS_BY_TYPE = "read_items_by_type";
    final SpringBootWebUtil springBootWebUtil;

    public ReadItemsController(SpringBootWebUtil springBootWebUtil) {
        this.springBootWebUtil = springBootWebUtil;
    }

    @GetMapping
    ResponseEntity<List<? extends Item>> doGet(@CookieValue(name = "userName") String userName,
                                               @CookieValue(name = "typeOfWork") int typeOfWork,
                                               @RequestParam String itemClassType) {
        return getResponse(userName, typeOfWork, itemClassType);
    }

    @PostMapping
    ResponseEntity<List<? extends Item>> doPost(@CookieValue(name = "userName") String userName,
                                                @CookieValue(name = "typeOfWork") int typeOfWork,
                                                @RequestBody String itemClassType) {
        return getResponse(userName, typeOfWork, itemClassType);
    }

    @NotNull
    private ResponseEntity<List<? extends Item>> getResponse(String userName, int typeOfWork, String itemClassType) {
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
