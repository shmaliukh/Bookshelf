package com.vshmaliukh.springwebappmodule.conrollers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.vshmaliukh.shelf.literature_items.ItemTitles;
import org.vshmaliukh.tomcat_web_app.utils.HtmlUtil;
import org.vshmaliukh.tomcat_web_app.utils.WebUtils;

import java.util.HashMap;
import java.util.Map;

import static com.vshmaliukh.springwebappmodule.conrollers.LogInController.TYPE_OF_WORK;
import static com.vshmaliukh.springwebappmodule.conrollers.LogInController.USER_NAME;
import static org.vshmaliukh.ConfigFile.typeOfWorkMap;

@Controller
public class MainMenuController {

    @GetMapping("/main-menu")
    public ModelAndView home(@RequestParam String userName,
                             @RequestParam int typeOfWork,
                             ModelMap model){
        model.addAttribute(USER_NAME, userName);
        model.addAttribute(TYPE_OF_WORK, typeOfWork);
        model.addAttribute("typeOfWorkFriendlyString", typeOfWorkMap.get(model.getAttribute(TYPE_OF_WORK)));

        Map<String, String> userAtrMap = new HashMap<>();
        userAtrMap.put(USER_NAME, userName);
        userAtrMap.put(TYPE_OF_WORK, String.valueOf(typeOfWork));

        String generatedMenuHtml = HtmlUtil.initMainMenu(userAtrMap) + WebUtils.generateCurrentStateOfShelf(userAtrMap, ItemTitles.TITLE_LIST);
        model.addAttribute("generatedMenu", generatedMenuHtml);

        return new ModelAndView("main-menu", model);
    }

}
