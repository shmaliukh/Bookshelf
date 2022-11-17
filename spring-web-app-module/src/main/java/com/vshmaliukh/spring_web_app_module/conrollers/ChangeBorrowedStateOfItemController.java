package com.vshmaliukh.spring_web_app_module.conrollers;

import com.vshmaliukh.spring_web_app_module.SpringBootWebUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.vshmaliukh.Constants;
import org.vshmaliukh.services.SaveReadShelfHandler;
import org.vshmaliukh.shelf.literature_items.Item;

import java.util.List;

import static org.vshmaliukh.Constants.CHANGE_ITEM_BORROWED_STATE_TITLE;

@Slf4j
@Controller
@RequestMapping("/" + CHANGE_ITEM_BORROWED_STATE_TITLE)
public class ChangeBorrowedStateOfItemController {

    final SpringBootWebUtil springBootWebUtil;

    public ChangeBorrowedStateOfItemController(SpringBootWebUtil springBootWebUtil) {
        this.springBootWebUtil = springBootWebUtil;
    }

    @GetMapping()
    ModelAndView changeBorrowedState(@CookieValue String userName,
                                     @CookieValue int typeOfWork,
                                     @RequestParam int indexOfItem,
                                     ModelMap modelMap) {
        changeBorrowedState(userName, typeOfWork, indexOfItem);
        return new ModelAndView("redirect:/" + Constants.EDIT_ITEMS_TITLE, modelMap);

    }

    private void changeBorrowedState(String userName, int typeOfWork, int indexOfItem) {
        SaveReadShelfHandler webShelfHandler = springBootWebUtil.generateSpringBootShelfHandler(userName, typeOfWork);
        if (webShelfHandler != null) {
            webShelfHandler.readShelfItems();
            List<Item> allLiteratureObjects = webShelfHandler.getShelf().getAllLiteratureObjects();
            webShelfHandler.changeBorrowedStateOfItem(allLiteratureObjects, indexOfItem);
            log.info("successful changed item borrowed state // item index: '{}'", indexOfItem);
        } else {
            log.warn("userName: '{}' " +
                            "// type of work: '{}' " +
                            "// problem to change borrowed state of item by '{}' index: webShelfHandler == null",
                    userName, typeOfWork, indexOfItem);
        }
    }

    @PostMapping()
    ResponseEntity<Void> changeBorrowedStateAndGetResponse(@CookieValue String userName,
                                                           @CookieValue int typeOfWork,
                                                           @RequestBody int indexOfItem) {
        SaveReadShelfHandler webShelfHandler = springBootWebUtil.generateSpringBootShelfHandler(userName, typeOfWork);
        Item itemToCheck = webShelfHandler.getShelf().getAllLiteratureObjects().get(indexOfItem - 1);
        itemToCheck.setBorrowed(!itemToCheck.isBorrowed());

        changeBorrowedState(userName, typeOfWork, indexOfItem);

        webShelfHandler.readShelfItems();
        List<Item> itemListAfterAction = webShelfHandler.getShelf().getAllLiteratureObjects();
        if (itemListAfterAction.contains(itemToCheck)) {
            return ResponseEntity.ok().build();
        } else {
            log.warn("userName: '{}' " +
                            "// type of work: '{}' " +
                            "// problem to change '{}' item borrowed state",
                    userName, typeOfWork, itemToCheck);
            return ResponseEntity.badRequest().build();
        }
    }

}
