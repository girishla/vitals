package com.kfc.vitals;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AppStatusController {

    @RequestMapping("/")
    @ResponseBody
    String home() {
      return "App seems to be runnning OK";
    }

}