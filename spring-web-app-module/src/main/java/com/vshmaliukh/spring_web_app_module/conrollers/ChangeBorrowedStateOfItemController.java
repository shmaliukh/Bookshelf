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
        SaveReadShelfHandler webShelfHandler = springBootWebUtil.generateSpringBootShelfHandler(userName, typeOfWork);
        if (webShelfHandler != null) {
            changeBorrowedState(indexOfItem, webShelfHandler);
        } else {
            log.warn("userName: '{}', type of work: '{}' // problem to change borrowed state of item by '{}' index: " +
                    "webShelfHandler == null", userName, typeOfWork, indexOfItem);
            log.debug("changeBorrowedState(userName: '{}', typeOfWork: '{}',indexOfItem: '{}', modelMap: '{}') // springBootWebUtil: '{}'",
                    userName, typeOfWork, indexOfItem, modelMap, springBootWebUtil);
        }
        return new ModelAndView("redirect:/" + Constants.EDIT_ITEMS_TITLE, modelMap);
    }

    private static void changeBorrowedState(int indexOfItem, SaveReadShelfHandler webShelfHandler) {
        webShelfHandler.readShelfItems();
        List<Item> allLiteratureObjects = webShelfHandler.getShelf().getAllLiteratureObjects();
        webShelfHandler.changeBorrowedStateOfItem(allLiteratureObjects, indexOfItem);
        log.info("successful changed item borrowed state // item index: '{}'", indexOfItem);
    }

    @PostMapping()
    <T extends Item> ResponseEntity<Void> changeBorrowedStateAndGetResponse(@CookieValue String userName,
                                                                            @CookieValue int typeOfWork,
                                                                            @RequestBody T item) {
        SaveReadShelfHandler webShelfHandler = springBootWebUtil.generateSpringBootShelfHandler(userName, typeOfWork);
        webShelfHandler.changeBorrowedStateOfItem(item);

        webShelfHandler.readShelfItems();
        T itemWithBorrowedState = item;
        itemWithBorrowedState.setBorrowed(!item.isBorrowed());
        if (webShelfHandler.getShelf().getAllLiteratureObjects().contains(itemWithBorrowedState)) {
            return ResponseEntity.ok().build();
        } else {
            log.warn("userName: '{}', type of work: '{}' // problem to change '{}' item borrowed state", userName, typeOfWork, item);
            return ResponseEntity.badRequest().build();
        }
    }

}
