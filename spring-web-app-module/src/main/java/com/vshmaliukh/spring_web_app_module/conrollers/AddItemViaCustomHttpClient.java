package com.vshmaliukh.spring_web_app_module.conrollers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping(AddItemViaCustomHttpClient.ADD_TEST)
public class AddItemViaCustomHttpClient {

   public static final String ADD_TEST = "/add_test";

   @PostMapping
   void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
      response.getWriter().println("asdasdasd");
   }


}
