package com.vshmaliukh.spring_web_app_module.conrollers.http;

import com.google.gson.Gson;
import com.vshmaliukh.spring_web_app_module.SpringBootWebUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import org.vshmaliukh.MyLogUtil;
import org.vshmaliukh.services.SaveReadShelfHandler;
import org.vshmaliukh.shelf.literature_items.Item;
import org.vshmaliukh.shelf.literature_items.ItemHandlerProvider;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Controller
public class ApacheController {


    public static final String READ_ITEMS = "/readItems";
    public static final String READ_ITEMS_BY_TYPE = "/readItemsByType";
    public static final String DEV_PAGE = "/dev";
    final SpringBootWebUtil springBootWebUtil;
    final Gson gson = new Gson();

    public ApacheController(SpringBootWebUtil springBootWebUtil) {
        this.springBootWebUtil = springBootWebUtil;
    }

    @GetMapping(READ_ITEMS_BY_TYPE)
    void readItemLisByClassTypeAsGsonStr(@CookieValue String userName,
                                         @CookieValue int typeOfWork,
                                         @CookieValue String classType,
                                         HttpServletResponse response) {
        SaveReadShelfHandler shelfHandler = springBootWebUtil.generateSpringBootShelfHandler(userName, typeOfWork);
        List<? extends Item> sortedItemsByClass = shelfHandler.getSortedItemsByClass(ItemHandlerProvider.getClassByName(classType));
        response.setContentType("application/json");

        try (PrintWriter responseWriter = response.getWriter()) {
            String sortedItemsByClassJsonStr = gson.toJson(sortedItemsByClass);
            responseWriter.write(sortedItemsByClassJsonStr);
            response.setStatus(HttpServletResponse.SC_OK);
            responseWriter.flush();
        } catch (IOException ioe) {
            MyLogUtil.logErr(this, ioe);
        }
    }

    @GetMapping(DEV_PAGE)
    void testPage() {
        RestTemplate restTemplate = new RestTemplate();
//        restTemplate
    }


}
