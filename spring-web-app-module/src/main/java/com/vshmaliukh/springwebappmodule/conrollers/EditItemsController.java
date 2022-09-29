package com.vshmaliukh.springwebappmodule.conrollers;

import com.vshmaliukh.springwebappmodule.utils.ControllerUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.vshmaliukh.utils.WebUtils;

import java.util.Map;

import static com.vshmaliukh.springwebappmodule.conrollers.MainMenuController.GENERATED_TABLE_HTML_STR;
import static org.vshmaliukh.ConfigFile.typeOfWorkMap;
import static org.vshmaliukh.Constants.EDIT_ITEMS_TITLE;
import static org.vshmaliukh.Constants.TYPE_OF_WORK_WITH_FILES;

@Controller
public class EditItemsController {

    @GetMapping("/" + EDIT_ITEMS_TITLE)
    ModelAndView doGet(@CookieValue String userName,
                       @CookieValue int typeOfWork,
                       ModelMap modelMap) {
        Map<String, String> userAtr = ControllerUtils.adaptUserAtrToWebAppStandard(userName, typeOfWork); // TODO refactor

        modelMap.addAttribute(TYPE_OF_WORK_WITH_FILES, typeOfWorkMap.get(typeOfWork));
        modelMap.addAttribute(GENERATED_TABLE_HTML_STR, WebUtils.generateTableForEditingItems(userAtr));
        return new ModelAndView(EDIT_ITEMS_TITLE, modelMap);
    }

}
