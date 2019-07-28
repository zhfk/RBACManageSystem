package com.auth.rbac.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GrantManageController {

    @GetMapping(value = "/manage/grant/user")
    public String grantUser(){
        return "grant_manage/user_grant";
    }

}
