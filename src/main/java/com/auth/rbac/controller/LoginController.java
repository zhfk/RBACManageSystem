package com.auth.rbac.controller;

import com.auth.rbac.dao.RLog;
import com.auth.rbac.service.RLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.Timestamp;

@Controller
@RequestMapping(value = "/")
public class LoginController {

    @Autowired
    private RLogService rLogService;

    @GetMapping(value = {"/", "/login"})
    public String login(){
        return "login";
    }

    @PostMapping(value = {"/logout"})
    public String logout(){
        rLogService.save(new RLog("--", "退出系统", new Timestamp(System.currentTimeMillis()), ""));
        return "login";
    }
}
