package com.auth.rbac.controller;

import com.auth.rbac.dao.ResponseData;
import com.auth.rbac.dao.Role;
import com.auth.rbac.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping(value = "/role/add")
    @ResponseBody
    public String addRole(@RequestParam(value = "name") String name,
                           @RequestParam(value = "desc") String desc){
        Optional<Role> opRole = roleService.getRoleByname(name);
        if(opRole.isPresent()){
            return opRole.get().getName()+" 已经存在了!";
        }else{
            Role role = new Role();
            role.setName(name);
            role.setDesc(desc);
            roleService.addRole(role);
            return "添加 " + name + " 成功!";
        }
    }

    @PostMapping(value = "/role/modify")
    @ResponseBody
    public String modifyRole(@RequestParam(value = "id") Integer id,
                          @RequestParam(value = "name") String name,
                          @RequestParam(value = "desc") String desc){
        Optional<Role> opRole = roleService.getRoleByname(name);
        if(opRole.isPresent() && !opRole.get().getId().equals(id)){
            return opRole.get().getName()+" 已经存在了!";
        }else{
            Role Role = roleService.getRoleById(id);
            Role.setName(name);
            Role.setDesc(desc);
            roleService.addRole(Role);
            return "修改 " + name + " 成功!";
        }
    }

    @GetMapping(value = "/role/getPageRole")
    @ResponseBody
    public ResponseData getPageRole(@RequestParam(value = "page") Integer page,
                                         @RequestParam(value = "limit") Integer limit){
        Page<Role> Roles = roleService.getRoleByPage(page-1, limit);
        return new ResponseData<>(0, "succeed", Roles.getTotalElements(), Roles.getContent());
    }

    @GetMapping(value = "/role/search")
    @ResponseBody
    public ResponseData search(@RequestParam(value = "page") Integer page,
                               @RequestParam(value = "limit") Integer limit,
                               @RequestParam(value = "name") String name){
        Page<Role> roles = roleService.findBynameLike(page-1, limit, name);
        return new ResponseData<>(0, "succeed", roles.getTotalElements(), roles.getContent());
    }

    @DeleteMapping(value = "/role/delete")
    @ResponseBody
    public void deleteRole(@RequestParam(value = "id") Integer id){
        roleService.deleteRole(id);
    }

    @DeleteMapping(value = "/role/batchDelete")
    @ResponseBody
    public void deleteRoles(@RequestParam(value = "id") String idList){
        roleService.deleteRoles(idList);
    }

}
