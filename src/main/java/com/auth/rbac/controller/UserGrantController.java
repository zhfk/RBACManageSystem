package com.auth.rbac.controller;

import com.auth.rbac.service.GrantService;
import com.auth.rbac.service.PrivilegeService;
import com.auth.rbac.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class UserGrantController {

    @Autowired
    private PrivilegeService privilegeService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private GrantService grantService;

    @GetMapping(value = "/grant/privilege")
    @ResponseBody
    public Map<String, List<String>> userResourceGrant(@RequestParam(value = "user") String user,
                                                       @RequestParam(value = "resource") String resource){
        Map<String, List<String>> privilege = new HashMap<>();
        List<List<String>> permissions = grantService.getPermission(user, resource);
        List<String> granted = permissions.stream().map(p -> p.get(2)).collect(Collectors.toList());
        List<Map<String, Object>> privilegs = privilegeService.getAllname();
        List<String> all = privilegs.stream().map(p -> (String)p.get("name")).collect(Collectors.toList());
        privilege.put("granted", granted);
        privilege.put("all", all);
        return privilege;
    }

    @GetMapping(value = "/grant/role")
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

}
