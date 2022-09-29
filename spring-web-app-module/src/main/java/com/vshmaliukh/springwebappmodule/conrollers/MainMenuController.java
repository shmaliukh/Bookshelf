package com.vshmaliukh.springwebappmodule.conrollers;

import com.vshmaliukh.springwebappmodule.utils.ControllerUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.vshmaliukh.shelf.literature_items.ItemTitles;

import java.util.Map;

import static com.vshmaliukh.springwebappmodule.SpringWebAppModuleApplication.GENERATED_TITTLE;
import static org.vshmaliukh.ConfigFile.typeOfWorkMap;
import static org.vshmaliukh.Constants.*;
import static org.vshmaliukh.utils.WebUtils.generateCurrentStateOfShelf;

@Controller
public class MainMenuController {

    public static final String GENERATED_TABLE_HTML_STR = "generatedTableHtmlStr";

    @GetMapping("/" + MAIN_MENU_TITLE)
    public ModelAndView doGet(@CookieValue(value = USER_NAME) String userName,
                              @CookieValue(value = TYPE_OF_WORK_WITH_FILES) int typeOfWork,
                              ModelMap model) {
        Map<String, String> userAtr = ControllerUtils.adaptUserAtrToWebAppStandard(userName, typeOfWork); // TODO refactor
        model.addAttribute(USER_NAME, userName);
        model.addAttribute(TYPE_OF_WORK_WITH_FILES, typeOfWorkMap.get(typeOfWork));
        model.addAttribute(GENERATED_TABLE_HTML_STR, generateCurrentStateOfShelf(userAtr, ItemTitles.TITLE_LIST));
        model.addAttribute(GENERATED_TITTLE, "Home");
        return new ModelAndView(MAIN_MENU_TITLE, model);
    }

}
