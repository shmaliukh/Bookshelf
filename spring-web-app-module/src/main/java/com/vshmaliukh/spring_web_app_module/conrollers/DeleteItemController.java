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

@Slf4j
@Controller
@RequestMapping("/" + Constants.DELETE_ITEM_TITLE)
public class DeleteItemController {

    final SpringBootWebUtil springBootWebUtil;

    public DeleteItemController(SpringBootWebUtil springBootWebUtil) {
        this.springBootWebUtil = springBootWebUtil;
    }

    @GetMapping()
    ModelAndView deleteItem(@CookieValue String userName,
                            @CookieValue int typeOfWork,
                            @RequestParam int indexOfItem,
                            ModelMap modelMap) {
        deleteItemByIndex(userName, typeOfWork, indexOfItem);
        return new ModelAndView("redirect:/" + Constants.EDIT_ITEMS_TITLE, modelMap);
    }

    private void deleteItemByIndex(String userName, int typeOfWork, int indexOfItem) {
        SaveReadShelfHandler webShelfHandler = springBootWebUtil.generateSpringBootShelfHandler(userName, typeOfWork);
        if (webShelfHandler != null) {
            webShelfHandler.deleteItemByIndex(indexOfItem);
        } else {
            log.warn("userName: '{}' " +
                            "// type of work: '{}' " +
                            "// problem to delete item by '{}' index: webShelfHandler == null",
                    userName, typeOfWork, indexOfItem);
        }
    }

    @PostMapping()
    ResponseEntity<Void> deleteItemAndGetResponse(@CookieValue String userName,
                                                  @CookieValue int typeOfWork,
                                                  @RequestBody int indexOfItem) {
        SaveReadShelfHandler webShelfHandler = springBootWebUtil.generateSpringBootShelfHandler(userName, typeOfWork);
        Item itemToDelete = webShelfHandler.getShelf().getAllLiteratureObjects().get(indexOfItem - 1);
        deleteItemByIndex(userName, typeOfWork, indexOfItem);

        webShelfHandler.readShelfItems();
        List<Item> itemListAfterAction = webShelfHandler.getShelf().getAllLiteratureObjects();
        if (!itemListAfterAction.contains(itemToDelete)) {
            return ResponseEntity.ok().build();
        } else {
            log.warn("userName: '{}' " +
                            "// type of work: '{}' " +
                            "// problem to change '{}' item borrowed state",
                    userName, typeOfWork, itemToDelete);
            return ResponseEntity.badRequest().build();
        }
    }

}
