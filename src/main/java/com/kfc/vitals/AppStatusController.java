package com.kfc.vitals;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AppStatusController {

    @RequestMapping("/")
    @ResponseBody
    String appStatus() {
      return "Vitals App is runnning and monitoring KFC services";
    }

}