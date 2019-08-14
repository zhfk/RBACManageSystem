package com.auth.rbac.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/")
public class LoginController {

    @GetMapping(value = {"/", "/login"})
    public String login(){
        return "login";
    }

    @PostMapping(value = {"/logout"})
    public String logout(){
        return "login";
    }
}
