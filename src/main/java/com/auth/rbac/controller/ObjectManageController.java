package com.auth.rbac.controller;

import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Api("swagger ui 注释 用户管理")
@Controller
public class ObjectManageController {

    @GetMapping(value = "/manage/user")
    public String userManage(){
        return "object_manage/user_manage";
    }

    @GetMapping(value = "/manage/relation/user")
    public String manageRelationUser(@RequestParam(value = "userId") String userId, ModelMap modelMap){
        modelMap.addAttribute("userId", userId);
        return "relation/user_relation_iframe";
    }

    @GetMapping(value = "/manage/add/user")
    public String manageAddUser(){
        return "user/add_user";
    }
}
