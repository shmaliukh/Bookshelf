package com.vshmaliukh.springwebappmodule.conrollers;

import com.vshmaliukh.springwebappmodule.utils.ControllerUtils;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.vshmaliukh.services.menus.menu_items.MenuItemForSorting;
import org.vshmaliukh.shelf.literature_items.ItemHandler;
import org.vshmaliukh.shelf.literature_items.ItemHandlerProvider;
import org.vshmaliukh.utils.UrlUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.vshmaliukh.springwebappmodule.SpringWebAppModuleApplication.GENERATED_HTML_STR;
import static com.vshmaliukh.springwebappmodule.SpringWebAppModuleApplication.GENERATED_TITTLE;
import static org.vshmaliukh.BootstrapHtmlBuilder.*;
import static org.vshmaliukh.Constants.*;

@Controller
@RequestMapping("/" + ITEMS_SORTING_MENU_TITLE)
public class ItemsSortingMenuController {

    @PostMapping()
    ModelAndView doPost(@RequestParam String menuItemIndex,
                        @RequestParam String itemClassType,
                        ModelMap modelMap) {
        modelMap.addAttribute(MENU_ITEM_INDEX, menuItemIndex);
        modelMap.addAttribute(ITEM_CLASS_TYPE, itemClassType);
        return new ModelAndView("redirect:/" + ITEMS_SORTING_MENU_TITLE, modelMap);
    }

    @GetMapping()
    ModelAndView doGet(@CookieValue String userName,
                       @CookieValue int typeOfWork,
                       @RequestParam(defaultValue = "") String menuItemIndex,
                       @RequestParam(defaultValue = "") String itemClassType,
                       ModelMap modelMap) {
        Map<String, String> userAtr = ControllerUtils.adaptUserAtrToWebAppStandard(userName, typeOfWork);
        String generateItemsTableStrOLD = ControllerUtils.generateItemsTableStr(userAtr, menuItemIndex, itemClassType, ItemHandlerProvider.getHandlerByName(itemClassType));

        ControllerUtils.formItemTableByClass(userName, typeOfWork, itemClassType, menuItemIndex, modelMap);
//        ControllerUtils.formRadioButtonsToSortItemsByValue(itemClassType, modelMap);

        Map<String, String> radioButtonsMap = new HashMap<>();
        ItemHandler handlerByName = ItemHandlerProvider.getHandlerByName(itemClassType);
        List<MenuItemForSorting> sortingMenuList = handlerByName.getSortingMenuList();
        for (MenuItemForSorting menuItemForSorting : sortingMenuList) {
            radioButtonsMap.put(String.valueOf(menuItemForSorting.getIndex()), menuItemForSorting.getStr());
        }
        modelMap.addAttribute("radioButtons", radioButtonsMap);



        modelMap.addAttribute(ITEM_CLASS_TYPE, itemClassType);
        modelMap.addAttribute(GENERATED_HTML_STR, generatePageHtmlText(itemClassType, generateItemsTableStrOLD));
        modelMap.addAttribute(GENERATED_TITTLE, itemClassType + " sorting");
        return new ModelAndView(ITEMS_SORTING_MENU_TITLE, modelMap);
    }

    private static String generatePageHtmlText(String itemClassType, String generateItemsTableStr) {
        StringBuilder sb = new StringBuilder();
        String generatedMenu = ControllerUtils.generateRadioButtonsMenuHtmlStr(ItemHandlerProvider.getHandlerByName(itemClassType).getSortingMenuList());
        URIBuilder uriBuilder = UrlUtil.generateBaseURLBuilder(ITEMS_SORTING_MENU_TITLE).addParameter(ITEM_CLASS_TYPE, itemClassType);
        sb.append(divContainer(
                htext("Choose '" + itemClassType + "' items type of sorting", "2") + split() +
                        form(uriBuilder, "post",
                                div(generatedMenu),
                                formSubmitButton()) +
                        split() +
                        div(generateItemsTableStr))).append(split()).append(buttonWithRef("Back", SORTING_TYPES_MENU_TITLE));
        return sb.toString();
    }

}
