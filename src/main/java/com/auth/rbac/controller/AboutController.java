package com.auth.rbac.controller;

import com.auth.rbac.dao.RLog;
import com.auth.rbac.service.RLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.Timestamp;

@Controller
@RequestMapping(value = "/api/v1/about")
public class AboutController {

    @Autowired
    private RLogService rLogService;

    @GetMapping(value = {"/",""})
    public String about(@AuthenticationPrincipal UserDetails userDetails, ModelMap model){
        rLogService.save(new RLog(userDetails.getUsername(), "查看about信息", new Timestamp(System.currentTimeMillis()), ""));
        model.addAttribute("desc","RBAC 权限管理");
        model.addAttribute("target","星辰大海");
        model.addAttribute("editor","zhfk");
        model.addAttribute("hobby","凉皮、沙拉、柠檬茶");
        model.addAttribute("version","0.0.1");
        return "other/about";
    }

}
