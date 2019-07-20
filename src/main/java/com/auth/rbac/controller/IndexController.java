package com.auth.rbac.controller;

import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Api("swagger ui 注释 User")
@Controller
public class IndexController {

    @GetMapping(value = "/")
    public String index(ModelMap model){
        return "index";
    }

    @GetMapping("/search")
    public String search(){
        return "search/search";
    }

    @GetMapping("/about")
    public String about(ModelMap model){
        model.addAttribute("desc","RBAC 权限管理");
        model.addAttribute("target","星辰大海");
        model.addAttribute("editor","zhfk");
        model.addAttribute("hobby","凉皮、沙拉、柠檬茶");
        model.addAttribute("version","0.0.1");
        return "other/about";
    }

    @GetMapping("/log")
    public String log(){
        return "log/audit";
    }
}
