package com.auth.rbac.controller;

import com.auth.rbac.dao.RLog;
import com.auth.rbac.service.*;
import org.casbin.jcasbin.main.Enforcer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/api/v1/grant")
public class GrantController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private GrantService grantService;

    @Autowired
    private ResourceBindPrivilegeService resourceBindPrivilegeService;

    @Autowired
    private Enforcer enforcer;

    @Autowired
    private RLogService rLogService;

    @GetMapping(value = {"/user"})
    public String grantUser(){
        return "grant_manage/user_grant";
    }

    @GetMapping(value = {"/resource"})
    public String grantResource(){
        return "grant_manage/resource_grant";
    }

    @GetMapping(value = "/privilege")
    @ResponseBody
    public Map<String, List<String>> userResourceGrant(@RequestParam(value = "user") String user,
                                                       @RequestParam(value = "resource") String resource){
        Map<String, List<String>> privilege = new HashMap<>();
        List<List<String>> permissions = grantService.getPermission(user, resource);
        List<String> granted = permissions.stream().map(p -> p.get(2)).collect(Collectors.toList());
        List<String> all = resourceBindPrivilegeService.getBindedPrivileges(resource);
        privilege.put("granted", granted);
        privilege.put("all", all);
        return privilege;
    }

    @GetMapping(value = "/role")
    @ResponseBody
    public Map<String, List<String>> userRoleGrant(@RequestParam(value = "user") String user){
        Map<String, List<String>> bindings = new HashMap<>();
        List<String> binded = grantService.getRole(user);
        List<Map<String, Object>> roles = roleService.getAllname();
        List<String> all = roles.stream().map(p -> (String)p.get("name")).collect(Collectors.toList());
        bindings.put("binded", binded);
        bindings.put("all", all);
        return bindings;
    }

    @PostMapping(value = "/apply")
    @ResponseBody
    public void userResourceGrant(@AuthenticationPrincipal UserDetails userDetails,
                                  @RequestParam(value = "user") String user,
                                 @RequestParam(value = "resource") String resource,
                                 @RequestParam(value = "grants") List<String> grants){
        for (String grant : grants) {
            rLogService.save(new RLog(userDetails.getUsername(), "授权："+user+"->"+resource+"->"+grant, new Timestamp(System.currentTimeMillis()), ""));
            enforcer.addPermissionForUser(user.toLowerCase(), resource.toLowerCase(), grant.toLowerCase());
        }
    }

    @PostMapping(value = "/revoke")
    @ResponseBody
    public void userResourceRevoke(@AuthenticationPrincipal UserDetails userDetails,
                                   @RequestParam(value = "user") String user,
                                   @RequestParam(value = "resource") String resource,
                                   @RequestParam(value = "grants") List<String> grants){
        for (String grant : grants) {
            rLogService.save(new RLog(userDetails.getUsername(), "撤销权限："+user+"->"+resource+"->"+grant, new Timestamp(System.currentTimeMillis()), ""));
            enforcer.deletePermissionForUser(user.toLowerCase(), resource.toLowerCase(), grant.toLowerCase());
        }
    }

}
