package com.auth.rbac.controller;

import com.auth.rbac.service.PrivilegeService;
import com.auth.rbac.service.ResourceService;
import com.auth.rbac.service.RoleService;
import com.auth.rbac.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class SearchController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private PrivilegeService privilegeService;

    @GetMapping(value = "/search/conditionList")
    @ResponseBody
    public Map<String, List<Map<String, Object>>> getSearchConditionList(){
        Map<String, List<Map<String, Object>>> map = new HashMap<>();
        List<Map<String, Object>> users = userService.getAllUsername();
        List<Map<String, Object>> roles = roleService.getAllname();
        List<Map<String, Object>> resources = resourceService.getAllname();
        List<Map<String, Object>> privileges = privilegeService.getAllname();
        map.put("users", users);
        map.put("roles", roles);
        map.put("resources", resources);
        map.put("privileges", privileges);
        return map;
    }

    @GetMapping(value = "/search/policy")
    @ResponseBody
    public void search(@RequestParam(value = "user")String user,
                       @RequestParam(value = "role")String role,
                       @RequestParam(value = "resource")String resource,
                       @RequestParam(value = "privilege")String privilege){
        System.out.println("user = [" + user + "], role = [" + role + "], resource = [" + resource + "], privilege = [" + privilege + "]");
    }

}
