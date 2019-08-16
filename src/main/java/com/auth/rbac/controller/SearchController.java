package com.auth.rbac.controller;

import com.auth.rbac.dao.RLog;
import com.auth.rbac.service.*;
import org.casbin.jcasbin.main.Enforcer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/api/v1/search")
public class SearchController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private PrivilegeService privilegeService;

    @Autowired
    private Enforcer enforcer;

    @Autowired
    private RLogService rLogService;

    @GetMapping(value = "/subList")
    @ResponseBody
    public Map<String, List<Map<String, Object>>> getSearchCondList(
            @RequestParam(value = "conds")String conds){
        Map<String, List<Map<String, Object>>> map = new HashMap<>();
        String[] ss = conds.split(",");
        for (String s : ss) {
            if ("users".equals(s)) {
                map.put("users", userService.getAllUsername());
            } else if ("roles".equals(s)) {
                map.put("roles", roleService.getAllname());
            } else if ("resources".equals(s)) {
                map.put("resources", resourceService.getAllname());
            } else if ("privileges".equals(s)) {
                map.put("privileges", privilegeService.getAllname());
            }
        }
        return map;
    }

    @GetMapping(value = "/policy")
    @ResponseBody
    public Boolean search(@AuthenticationPrincipal UserDetails userDetails,
                          @RequestParam(value = "subject", defaultValue = "")String subject,
                          @RequestParam(value = "resource", defaultValue = "")String resource,
                          @RequestParam(value = "privilege", defaultValue = "")String privilege){

        rLogService.save(new RLog(userDetails.getUsername(), "权限验证："+subject+"->"+resource+"->"+privilege, new Timestamp(System.currentTimeMillis()), ""));
        return enforcer.enforce(subject.toLowerCase(), resource.toLowerCase(), privilege.toLowerCase());
    }

    @GetMapping(value = {"/",""})
    public String search(){
        return "search/search";
    }

}
