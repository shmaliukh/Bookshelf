package com.vshmaliukh.springwebappmodule.conrollers;

import com.vshmaliukh.springwebappmodule.SpringAppUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.vshmaliukh.Constants;
import org.vshmaliukh.services.SaveReadShelfHandler;
import org.vshmaliukh.services.menus.GeneratedMenu;
import org.vshmaliukh.shelf.literature_items.Item;
import org.vshmaliukh.shelf.literature_items.ItemHandler;
import org.vshmaliukh.shelf.literature_items.ItemHandlerProvider;
import org.vshmaliukh.utils.HtmlUtil;
import org.vshmaliukh.utils.UrlUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.vshmaliukh.Constants.ITEM_CLASS_TYPE;
import static org.vshmaliukh.utils.HtmlUtil.generateMenuItemsFormHTML;
import static org.vshmaliukh.utils.WebUtils.generateShelfHandler;
import static org.vshmaliukh.utils.WebUtils.generateTableOfShelfItems;

@Controller
public class ItemsSortingMenuController {

    @PostMapping("/" + Constants.ITEMS_SORTING_MENU_TITLE)
    ModelAndView doPost(@RequestParam String menuItemIndex,
                        @RequestParam String itemClassType,
                        ModelMap model) {
        model.addAttribute(Constants.MENU_ITEM_INDEX, menuItemIndex);
        model.addAttribute(ITEM_CLASS_TYPE, itemClassType);
        return new ModelAndView("redirect:/" + Constants.ITEMS_SORTING_MENU_TITLE, model);
    }

    @GetMapping("/" + Constants.ITEMS_SORTING_MENU_TITLE)
    ModelAndView doGet(@CookieValue String userName,
                       @CookieValue int typeOfWork,
                       @RequestParam(defaultValue = "") String menuItemIndex,
                       @RequestParam(defaultValue = "") String itemClassType,
                       ModelMap model) {
        model.addAttribute(Constants.MENU_ITEM_INDEX, menuItemIndex);
        model.addAttribute(ITEM_CLASS_TYPE, itemClassType);
        Map<String, String> userAtr = ControllerUtils.adaptUserAtrToWebAppStandard(userName, typeOfWork);

        StringBuilder stringBuilder = new StringBuilder();
        if (itemClassType != null && !itemClassType.equals("")) {
            ItemHandler<?> handlerByName = ItemHandlerProvider.getHandlerByName(itemClassType);
            stringBuilder.append(GeneratedMenu.MESSAGE_TO_ENTER + " <br>\n");
            stringBuilder.append(initMenu(userAtr, itemClassType, handlerByName)); // TODO
            generateItemsTableStr(userAtr, stringBuilder, menuItemIndex, itemClassType, handlerByName);
        }

        stringBuilder.append(HtmlUtil.formHTMLButton(SpringAppUtils.generateUrlString(Constants.SORTING_TYPES_MENU_TITLE), Constants.SORTING_TYPES_MENU_TITLE));

//        stringBuilder.append(request.getParameter(INFORM_MESSAGE));
        model.addAttribute("generatedHtmlStr", stringBuilder.toString());
        return new ModelAndView(Constants.ITEMS_SORTING_MENU_TITLE, model);
    }

    private String initMenu(Map userAtr, String itemClassType, ItemHandler<?> handlerByName) {
        return generateMenuItemsFormHTML(UrlUtil.generateBaseURLBuilder(Constants.ITEMS_SORTING_MENU_TITLE, userAtr)
                        .addParameter(ITEM_CLASS_TYPE, itemClassType),
                handlerByName.getSortingMenuList());
    }

    private <T extends Item> void generateItemsTableStr(Map<String, String> userAtr, StringBuilder stringBuilder, String menuIndexStr, String classTypeStr, ItemHandler<T> handlerByName) {
        Class classType = ItemHandlerProvider.getClassByName(classTypeStr);
        SaveReadShelfHandler webShelfHandler = generateShelfHandler(userAtr);
        List<T> sortedItemsByClass = new ArrayList<>();
        if (webShelfHandler != null) {
            sortedItemsByClass = webShelfHandler.getSortedItemsByClass(classType);
        }
        if (menuIndexStr != null && !menuIndexStr.equals("")) {
            int typeOfSorting = Integer.parseInt(menuIndexStr);
            List<T> sortedList = handlerByName.getSortedItems(typeOfSorting, sortedItemsByClass);
            stringBuilder.append(generateTableOfShelfItems(sortedList, true));
        } else {
            stringBuilder.append(generateTableOfShelfItems(sortedItemsByClass, true));
        }
    }
}
