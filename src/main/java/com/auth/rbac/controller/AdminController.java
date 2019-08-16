package com.auth.rbac.controller;

import com.auth.rbac.dao.Admin;
import com.auth.rbac.dao.RLog;
import com.auth.rbac.service.AdminService;
import com.auth.rbac.service.RLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.Timestamp;

@Controller
@RequestMapping(value = "/api/v1/admin")
public class AdminController {

    @Autowired
    private RLogService rLogService;

    @Autowired
    private AdminService adminService;

    @GetMapping(value = "/info")
    public String getAdminInfo(@AuthenticationPrincipal UserDetails userDetails, ModelMap modelMap){
        rLogService.save(new RLog(userDetails.getUsername(), "查看admin信息", new Timestamp(System.currentTimeMillis()), ""));
        Admin admin = adminService.getAdminInfo();
        modelMap.addAttribute("admin", admin);
        return "admin/admin";
    }

    @PostMapping(value = "/update")
    public String update(@AuthenticationPrincipal UserDetails userDetails, Admin admin, ModelMap modelMap){
        rLogService.save(new RLog(userDetails.getUsername(), "更新admin信息", new Timestamp(System.currentTimeMillis()), ""));
        Boolean succeed = adminService.update(admin);
        modelMap.addAttribute("succeed", succeed);
        modelMap.addAttribute("admin", admin);
        return "admin/admin";
    }

}
