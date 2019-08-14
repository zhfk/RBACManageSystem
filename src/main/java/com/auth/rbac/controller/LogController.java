package com.auth.rbac.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/api/v1/log")
public class LogController {
    @GetMapping(value = {"/",""})
    public String log(){
        return "log/audit";
    }
}
