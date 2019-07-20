package com.auth.rbac.controller;

import io.swagger.annotations.Api;
import org.casbin.exception.CasbinAdapterException;
import org.casbin.jcasbin.main.Enforcer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Api("swagger ui 注释 enforcer")
@RestController
public class EnforcerController {

    private static final Logger logger = LoggerFactory.getLogger(EnforcerController.class);

    @Autowired
    private Enforcer enforcer;

    @GetMapping(value = "/enforce")
    public Boolean enforce(@RequestParam(name = "sub") String sub,
                           @RequestParam(name = "obj") String obj,
                           @RequestParam(name = "act") String act){
        return enforcer.enforce(sub, obj, act);
    }

    @PostMapping(value = "/policy/add")
    public Boolean addPolicy(
            @RequestParam(name = "sub") String sub,
            @RequestParam(name = "obj") String obj,
            @RequestParam(name = "act") String act){
        return enforcer.addPolicy(sub, obj, act);
    }

    @PostMapping(value = "/groupPolicy/add")
    public Boolean addGroupPolicy(@RequestParam(name = "sub") String sub,
                                  @RequestParam(name = "group") String group) {
        return enforcer.addGroupingPolicy(sub, group);
    }

    @GetMapping(value = "/policy/getAll")
    public List<List<String>> getAllPolicy(){
        return enforcer.getPolicy();
    }

    @GetMapping(value = "/groupPolicy/getAll")
    public List<List<String>> getAllGroupPolicy(){
        return enforcer.getGroupingPolicy();
    }

    @GetMapping(value = "/permission")
    public List<List<String>> getPermissionsForUser(@RequestParam(name = "user") String user){
       return enforcer.getPermissionsForUser(user);
    }

    @GetMapping(value = "/implicitPermission")
    public List<List<String>> getImplicitPermissionsForUser(@RequestParam(name = "user") String user){
        List<List<String>> res = new ArrayList<>();
        try{
            res = enforcer.getImplicitPermissionsForUser(user);
        }catch (IllegalArgumentException e){
            logger.warn(e.getMessage());
        }
        return res;
    }

    @DeleteMapping(value = "/policy/delete")
    public boolean removePolicy(@RequestParam(name = "sub") String sub,
                                @RequestParam(name = "obj") String obj,
                                @RequestParam(name = "act") String act){
        boolean succeed = false;
        try{
            succeed = enforcer.removePolicy(sub, obj, act);
        }catch (CasbinAdapterException e){
            logger.warn(e.getMessage());
        }
        return succeed;
    }

    @DeleteMapping(value = "/groupPolicy/delete")
    public boolean removeGroupPolicy(@RequestParam(name = "sub") String sub,
                                     @RequestParam(name = "group") String group) {
        boolean succeed = false;
        try{
            succeed = enforcer.removeGroupingPolicy(sub, group);
        }catch (CasbinAdapterException e){
            logger.warn(e.getMessage());
        }
        return succeed;
    }

}
