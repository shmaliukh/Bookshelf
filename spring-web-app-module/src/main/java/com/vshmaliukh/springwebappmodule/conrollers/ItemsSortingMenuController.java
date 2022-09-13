package com.vshmaliukh.springwebappmodule.conrollers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.vshmaliukh.services.SaveReadShelfHandler;
import org.vshmaliukh.services.menus.GeneratedMenu;
import org.vshmaliukh.shelf.literature_items.Item;
import org.vshmaliukh.shelf.literature_items.ItemHandler;
import org.vshmaliukh.shelf.literature_items.ItemHandlerProvider;
import org.vshmaliukh.tomcat_web_app.utils.HtmlUtil;
import org.vshmaliukh.tomcat_web_app.utils.UrlUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.vshmaliukh.tomcat_web_app.ShelfWebApp.ITEMS_SORTING_MENU_TITLE;
import static org.vshmaliukh.tomcat_web_app.ShelfWebApp.SORTING_TYPES_MENU_TITLE;
import static org.vshmaliukh.tomcat_web_app.servlets.AddMenuServlet.ITEM_CLASS_TYPE;
import static org.vshmaliukh.tomcat_web_app.utils.HtmlUtil.generateMenuItemsFormHTML;
import static org.vshmaliukh.tomcat_web_app.utils.WebUtils.*;

@Controller
public class ItemsSortingMenuController {

    @PostMapping("/" + ITEMS_SORTING_MENU_TITLE)
    ModelAndView doPost(@RequestParam String menuItemIndex,
                        @RequestParam String itemClassType,
                        ModelMap model) {
        model.addAttribute(MENU_ITEM_INDEX, menuItemIndex);
        model.addAttribute(ITEM_CLASS_TYPE, itemClassType);
        return new ModelAndView("redirect:/" + ITEMS_SORTING_MENU_TITLE, model);
    }

    @GetMapping("/" + ITEMS_SORTING_MENU_TITLE)
    ModelAndView doGet(@CookieValue String userName,
                       @CookieValue int typeOfWork,
                       @RequestParam(defaultValue = "") String menuItemIndex,
                       @RequestParam(defaultValue = "") String itemClassType,
                       ModelMap model) {
        model.addAttribute(MENU_ITEM_INDEX, menuItemIndex);
        model.addAttribute(ITEM_CLASS_TYPE, itemClassType);
        Map<String, String> userAtr = ControllerUtils.adaptUserAtrToWebAppStandard(userName, typeOfWork);

        StringBuilder stringBuilder = new StringBuilder();
        if (itemClassType != null && !itemClassType.equals("")) {
            ItemHandler<?> handlerByName = ItemHandlerProvider.getHandlerByName(itemClassType);
            stringBuilder.append(GeneratedMenu.MESSAGE_TO_ENTER + " <br>\n");
            stringBuilder.append(initMenu(userAtr, itemClassType, handlerByName)); // TODO
            generateItemsTableStr(userAtr, stringBuilder, menuItemIndex, itemClassType, handlerByName);
        }

        stringBuilder.append(HtmlUtil.formHTMLButton(UrlUtil.generateBaseURLString(SORTING_TYPES_MENU_TITLE, userAtr), SORTING_TYPES_MENU_TITLE));

//        stringBuilder.append(request.getParameter(INFORM_MESSAGE));
        model.addAttribute("generatedHtmlStr", stringBuilder.toString());
        return new ModelAndView(ITEMS_SORTING_MENU_TITLE, model);
    }

    private String initMenu(Map userAtr, String itemClassType, ItemHandler<?> handlerByName) {
        return generateMenuItemsFormHTML(UrlUtil.generateBaseURLBuilder(ITEMS_SORTING_MENU_TITLE, userAtr)
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
