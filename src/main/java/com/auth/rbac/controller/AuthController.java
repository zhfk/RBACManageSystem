package com.auth.rbac.controller;

import com.auth.rbac.dao.RLog;
import com.auth.rbac.service.RLogService;
import org.casbin.jcasbin.main.Enforcer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;

@RestController
public class AuthController {

    private Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    Enforcer enforcer;

    @Autowired
    private RLogService rLogService;

    @GetMapping("/enforce")
    public Boolean sqlBasedAuth(@RequestParam(value = "subject", defaultValue = "")String subject,
                                        @RequestParam(value = "resource", defaultValue = "")String resource,
                                        @RequestParam(value = "privilege", defaultValue = "")String privilege){
        rLogService.save(new RLog("--", "验证权限："+subject+"，"+resource+"，"+privilege, new Timestamp(System.currentTimeMillis()), ""));
        return enforcer.enforce(subject.toLowerCase(), resource.toLowerCase(), privilege.toLowerCase());
    }
}
