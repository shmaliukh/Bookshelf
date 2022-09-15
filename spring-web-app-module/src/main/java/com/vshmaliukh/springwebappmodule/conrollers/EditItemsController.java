package com.vshmaliukh.springwebappmodule.conrollers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.vshmaliukh.Constants;
import org.vshmaliukh.utils.WebUtils;

import javax.servlet.http.HttpServlet;
import java.util.Map;

import static org.vshmaliukh.BootstrapHtmlBuilder.*;

@Controller
public class EditItemsController extends HttpServlet {

    @GetMapping("/" + Constants.EDIT_ITEMS_TITLE)
    ModelAndView doGet(@CookieValue String userName,
                       @CookieValue int typeOfWork,
                       ModelMap model) {
        Map<String, String> userAtr = ControllerUtils.adaptUserAtrToWebAppStandard(userName, typeOfWork);

        StringBuilder sb = new StringBuilder();
        sb.append(div("class=\"row g-4  row-cols-1 row-cols-lg-2\"",
                htext("You are able to edit items", "2") +
                        div(htext("Type of work with files: ", "3") +
                                htext(ControllerUtils.getFriendlyTypeOfWorkStr(typeOfWork), "3")
                        )
        ));
        sb.append(split());
        sb.append(WebUtils.generateTableForEditingItems(userAtr));
        sb.append(split());
        sb.append(buttonWithRef("Back", Constants.MAIN_MENU_TITLE));

        model.addAttribute("generatedHtmlStr", sb.toString());

        return new ModelAndView(Constants.EDIT_ITEMS_TITLE, model);
    }
}
