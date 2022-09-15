package com.vshmaliukh.springwebappmodule.conrollers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.vshmaliukh.Constants;
import org.vshmaliukh.shelf.literature_items.ItemTitles;

import java.util.Map;

import static org.vshmaliukh.BootstrapHtmlBuilder.*;
import static com.vshmaliukh.springwebappmodule.SpringWebAppModuleApplication.*;
import static org.vshmaliukh.ConfigFile.typeOfWorkMap;
import static org.vshmaliukh.Constants.*;
import static org.vshmaliukh.utils.WebUtils.generateCurrentStateOfShelf;

@Controller
public class MainMenuController {

    @GetMapping("/" + Constants.MAIN_MENU_TITLE)
    public ModelAndView doGet(@CookieValue(value = USER_NAME) String userName,
                              @CookieValue(value = TYPE_OF_WORK_WITH_FILES) int typeOfWork,
                              ModelMap model) {
        model.addAttribute(USER_NAME, userName);
        model.addAttribute(TYPE_OF_WORK_WITH_FILES, typeOfWork);
        Map<String, String> userAtr = ControllerUtils.adaptUserAtrToWebAppStandard(userName, typeOfWork); // TODO refactor

        String friendlyTypeOfWorkStr = typeOfWorkMap.get(model.getAttribute(TYPE_OF_WORK_WITH_FILES));
        model.addAttribute(GENERATED_HTML_STR, generateMainMenuHtmlText(userAtr, friendlyTypeOfWorkStr));

        model.addAttribute(GENERATED_TITTLE, "Home");
        return new ModelAndView(BASE_PAGE_WITH_PLACEHOLDER, model);
    }

    private static String generateMainMenuHtmlText(Map<String, String> userAtr, String friendlyTypeOfWorkStr) {
        StringBuilder sb = new StringBuilder();
        sb.append(divContainer(
                div("class=\"row g-4  row-cols-1 row-cols-lg-2\"",
                        htext("Welcome, " + userAtr.get(USER_NAME), "1") +
                                div(htext("Type of work with files: ", "3") +
                                        htext(friendlyTypeOfWorkStr, "3")
                                )
                )
        ));
        sb.append(split());
        sb.append(divContainer(
                buttonWithRef("Add item", ADD_MENU_TITLE) +
                        buttonWithRef("Edit items", EDIT_ITEMS_TITLE) +
                        buttonWithRef("Sort items", SORTING_TYPES_MENU_TITLE) +
                        buttonWithRef("Change type of work", CHOOSE_TYPE_OF_WORK_TITLE) +
                        buttonWithRef("Exit", LOG_IN_TITLE)
        ));
        sb.append(split());
        sb.append(divContainer(
                description("Current state of bookshelf") +
                        generateCurrentStateOfShelf(userAtr, ItemTitles.TITLE_LIST)
        ));
        return sb.toString();
    }
}
