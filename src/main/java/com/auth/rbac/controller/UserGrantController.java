package com.auth.rbac.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserGrantController {

    @PostMapping(value = "/grant/user")
    @ResponseBody
    public String userResourceGrant(@RequestParam(value = "user") String user,
                                    @RequestParam(value = "resource") String resource){
        System.out.println("UserGrantController.userResourceGrant");
        return "userResourceGrant";
    }

}
