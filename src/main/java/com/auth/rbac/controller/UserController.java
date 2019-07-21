package com.auth.rbac.controller;

import com.auth.rbac.dao.User;
import com.auth.rbac.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Api("swagger ui 注释 User")
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/user/all")
    public List getAllUser(){
        return userService.getAllUser();
    }

    @PostMapping(value = "/user/add")
    public void addUser(@RequestParam(name = "name") String name,
                        @RequestParam(name = "account") String account,
                        @RequestParam(name = "pass") String pass){
    }

    @GetMapping(value = "/user/info")
    public String getPersonalInfo(){
        return "user/info";
    }

    @GetMapping(value = "/login")
    public String logout(){
        return "login";
    }

}
