package com.vshmaliukh.springwebappmodule.conrollers;

import com.vshmaliukh.springwebappmodule.utils.ControllerUtils;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.vshmaliukh.Constants;
import org.vshmaliukh.shelf.literature_items.ItemHandlerProvider;
import org.vshmaliukh.utils.UrlUtil;

import java.util.Map;

import static com.vshmaliukh.springwebappmodule.SpringWebAppModuleApplication.*;
import static org.vshmaliukh.BootstrapHtmlBuilder.*;
import static org.vshmaliukh.Constants.*;

@Controller
@RequestMapping("/" + Constants.ITEMS_SORTING_MENU_TITLE)
public class ItemsSortingMenuController {

    @PostMapping()
    ModelAndView doPost(@RequestParam String menuItemIndex,
                        @RequestParam String itemClassType,
                        ModelMap modelMap) {
        modelMap.addAttribute(Constants.MENU_ITEM_INDEX, menuItemIndex);
        modelMap.addAttribute(ITEM_CLASS_TYPE, itemClassType);
        return new ModelAndView("redirect:/" + Constants.ITEMS_SORTING_MENU_TITLE, modelMap);
    }

    @GetMapping()
    ModelAndView doGet(@CookieValue String userName,
                       @CookieValue int typeOfWork,
                       @RequestParam(defaultValue = "") String menuItemIndex,
                       @RequestParam(defaultValue = "") String itemClassType,
                       ModelMap modelMap) {
        Map<String, String> userAtr = ControllerUtils.adaptUserAtrToWebAppStandard(userName, typeOfWork);
        String generateItemsTableStr = ControllerUtils.generateItemsTableStr(userAtr, menuItemIndex, itemClassType, ItemHandlerProvider.getHandlerByName(itemClassType));

        modelMap.addAttribute(GENERATED_HTML_STR, generatePageHtmlText(itemClassType, generateItemsTableStr));
        modelMap.addAttribute(GENERATED_TITTLE, itemClassType + " sorting");
        return new ModelAndView(BASE_PAGE_WITH_PLACEHOLDER, modelMap);
    }

    private static String generatePageHtmlText(String itemClassType, String generateItemsTableStr) { // FIXME create template
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
