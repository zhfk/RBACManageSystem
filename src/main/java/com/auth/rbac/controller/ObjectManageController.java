package com.auth.rbac.controller;

import com.auth.rbac.dao.User;
import com.auth.rbac.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Api("swagger ui 注释 用户管理")
@Controller
public class ObjectManageController {

    @Autowired
    private UserService userService;

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

    @GetMapping(value = "/manage/user/info")
    public String manageUserInfo(@RequestParam(value = "userId") Long userId, ModelMap modelMap){
        User user = userService.getUserById(userId);
        modelMap.addAttribute("user",user);
        return "user/user_info";
    }


}
