package com.auth.rbac.controller;

import com.auth.rbac.service.GrantService;
import com.auth.rbac.service.PrivilegeService;
import com.auth.rbac.service.RoleService;
import org.casbin.jcasbin.main.Enforcer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/api/v1/grant")
public class GrantController {

    @Autowired
    private PrivilegeService privilegeService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private GrantService grantService;

    @Autowired
    private Enforcer enforcer;

    @GetMapping(value = {"/",""})
    public String grantUser(){
        return "grant_manage/user_grant";
    }

    @GetMapping(value = "/privilege")
    @ResponseBody
    public Map<String, List<String>> userResourceGrant(@RequestParam(value = "user") String user,
                                                       @RequestParam(value = "resource") String resource){
        Map<String, List<String>> privilege = new HashMap<>();
        List<List<String>> permissions = grantService.getPermission(user, resource);
        List<String> granted = permissions.stream().map(p -> p.get(2)).collect(Collectors.toList());
        List<Map<String, Object>> privilegs = privilegeService.getAllname(resource);
        List<String> all = privilegs.stream().map(p -> (String)p.get("name")).collect(Collectors.toList());
        privilege.put("granted", granted);
        privilege.put("all", all);
        return privilege;
    }

    @GetMapping(value = "/role")
    @ResponseBody
    public Map<String, List<String>> userRoleGrant(@RequestParam(value = "user") String user){
        Map<String, List<String>> bindings = new HashMap<>();
        List<String> binded = grantService.getRole(user);
        List<Map<String, Object>> privilegs = roleService.getAllname();
        List<String> all = privilegs.stream().map(p -> (String)p.get("name")).collect(Collectors.toList());
        bindings.put("binded", binded);
        bindings.put("all", all);
        return bindings;
    }

    @PostMapping(value = "/apply")
    @ResponseBody
    public void userResourceGrant(@RequestParam(value = "user") String user,
                                 @RequestParam(value = "resource") String resource,
                                 @RequestParam(value = "grants") List<String> grants){
        for (String grant : grants) {
            enforcer.addPermissionForUser(user, resource, grant);
        }
    }

    @PostMapping(value = "/revoke")
    @ResponseBody
    public void userResourceRevoke(@RequestParam(value = "user") String user,
                                  @RequestParam(value = "resource") String resource,
                                  @RequestParam(value = "grants") List<String> grants){
        for (String grant : grants) {
            enforcer.deletePermissionForUser(user, resource, grant);
        }
    }

}
