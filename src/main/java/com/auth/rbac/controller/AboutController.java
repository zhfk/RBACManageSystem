package com.auth.rbac.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/api/v1/about")
public class AboutController {

    @GetMapping(value = {"/",""})
    public String about(ModelMap model){
        model.addAttribute("desc","RBAC 权限管理");
        model.addAttribute("target","星辰大海");
        model.addAttribute("editor","zhfk");
        model.addAttribute("hobby","凉皮、沙拉、柠檬茶");
        model.addAttribute("version","0.0.1");
        return "other/about";
    }

}
