package com.auth.rbac.controller;

import com.auth.rbac.dao.Admin;
import com.auth.rbac.service.AdminService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Api("swagger ui 注释 Admin")
@Controller
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping(value = "/admin/info")
    public String getAdminInfo(ModelMap modelMap){
        Admin admin = adminService.getAdminInfo();
        modelMap.addAttribute("admin", admin);
        return "admin/admin";
    }

    @PostMapping(value = "/admin/update")
    public String update(Admin admin, ModelMap modelMap){
        String pass = null;
        if(StringUtils.isEmpty(admin.getPassword())){
            pass = adminService.getAdminInfo().getPassword();
            admin.setPassword(pass);
        }
        Boolean succeed = adminService.update(admin);
        modelMap.addAttribute("succeed", succeed);
        modelMap.addAttribute("admin", admin);
        return "admin/admin";
    }

}
