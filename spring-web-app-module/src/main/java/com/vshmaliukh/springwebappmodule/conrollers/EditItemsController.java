package com.vshmaliukh.springwebappmodule.conrollers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.vshmaliukh.Constants;
import org.vshmaliukh.utils.WebUtils;

import java.util.Map;

import static com.vshmaliukh.springwebappmodule.SpringWebAppModuleApplication.*;
import static org.vshmaliukh.BootstrapHtmlBuilder.*;

@Controller
public class EditItemsController {

    @GetMapping("/" + Constants.EDIT_ITEMS_TITLE)
    ModelAndView doGet(@CookieValue String userName,
                       @CookieValue int typeOfWork,
                       ModelMap modelMap) {
        Map<String, String> userAtr = ControllerUtils.adaptUserAtrToWebAppStandard(userName, typeOfWork); // TODO refactor

        modelMap.addAttribute(GENERATED_HTML_STR, generatePageHtmlText(typeOfWork, userAtr));
        modelMap.addAttribute(GENERATED_TITTLE, "Edit items");
        return new ModelAndView(BASE_PAGE_WITH_PLACEHOLDER, modelMap);
    }

    private static String generatePageHtmlText(int typeOfWork, Map<String, String> userAtr) {
        return div("class=\"row g-4  row-cols-1 row-cols-lg-2\"",
                htext("You are able to edit items", "2") +
                        div(htext("Type of work with files: ", "3") +
                                htext(ControllerUtils.getFriendlyTypeOfWorkStr(typeOfWork), "3")
                        )) +
                split() +
                WebUtils.generateTableForEditingItems(userAtr) +
                split() +
                buttonWithRef("Back", Constants.MAIN_MENU_TITLE);
    }
}
