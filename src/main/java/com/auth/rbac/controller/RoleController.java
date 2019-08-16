package com.auth.rbac.controller;

import com.auth.rbac.dao.RLog;
import com.auth.rbac.dao.ResponseData;
import com.auth.rbac.dao.Role;
import com.auth.rbac.service.RLogService;
import com.auth.rbac.service.RoleService;
import org.casbin.jcasbin.main.Enforcer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/api/v1/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private Enforcer enforcer;

    @Autowired
    private RLogService rLogService;

    @PostMapping(value = "/add")
    @ResponseBody
    public String addRole(@AuthenticationPrincipal UserDetails userDetails,
                          @RequestParam(value = "name") String name,
                          @RequestParam(value = "desc") String desc){
        Optional<Role> opRole = roleService.getRoleByname(name);
        if(opRole.isPresent()){
            return opRole.get().getName()+" 已经存在了!";
        }else{
            rLogService.save(new RLog(userDetails.getUsername(), "添加组："+name, new Timestamp(System.currentTimeMillis()), ""));
            Role role = new Role();
            role.setName(name);
            role.setDesc(desc);
            roleService.addRole(role);
            return "添加 " + name + " 成功!";
        }
    }

    @PostMapping(value = "/modify")
    @ResponseBody
    public String modifyRole(@AuthenticationPrincipal UserDetails userDetails,
                             @RequestParam(value = "id") Integer id,
                             @RequestParam(value = "name") String name,
                             @RequestParam(value = "desc") String desc){
        Optional<Role> opRole = roleService.getRoleByname(name);
        if(opRole.isPresent() && !opRole.get().getId().equals(id)){
            return opRole.get().getName()+" 已经存在了!";
        }else{
            rLogService.save(new RLog(userDetails.getUsername(), "修改组："+name, new Timestamp(System.currentTimeMillis()), ""));
            Role Role = roleService.getRoleById(id);
            Role.setName(name);
            Role.setDesc(desc);
            roleService.addRole(Role);
            return "修改 " + name + " 成功!";
        }
    }

    @GetMapping(value = "/getPageRole")
    @ResponseBody
    public ResponseData getPageRole(@RequestParam(value = "page") Integer page,
                                         @RequestParam(value = "limit") Integer limit){
        Page<Role> Roles = roleService.getRoleByPage(page-1, limit);
        return new ResponseData<>(0, "succeed", Roles.getTotalElements(), Roles.getContent());
    }

    @GetMapping(value = "/search")
    @ResponseBody
    public ResponseData search(@RequestParam(value = "page") Integer page,
                               @RequestParam(value = "limit") Integer limit,
                               @RequestParam(value = "name") String name){
        Page<Role> roles = roleService.findBynameLike(page-1, limit, name);
        return new ResponseData<>(0, "succeed", roles.getTotalElements(), roles.getContent());
    }

    @DeleteMapping(value = "/delete")
    @ResponseBody
    public void deleteRole(@AuthenticationPrincipal UserDetails userDetails,
                           @RequestParam(value = "id") Integer id,
                           @RequestParam(value = "name") String name){
        rLogService.save(new RLog(userDetails.getUsername(), "删除组："+name, new Timestamp(System.currentTimeMillis()), ""));
        roleService.deleteRole(id);
    }

    @DeleteMapping(value = "/batchDelete")
    @ResponseBody
    public void deleteRoles(@AuthenticationPrincipal UserDetails userDetails,
                            @RequestParam(value = "id") String idList,
                            @RequestParam(value = "name") String names){
        rLogService.save(new RLog(userDetails.getUsername(), "批量删除组："+names, new Timestamp(System.currentTimeMillis()), ""));
        roleService.deleteRoles(idList);
    }

    @PostMapping(value = "/bind")
    @ResponseBody
    public void bind(@RequestParam(value = "user") String user,
                     @RequestParam(value = "roles") List<String> roles,
                     @AuthenticationPrincipal UserDetails userDetails){
        for (String role : roles) {
            rLogService.save(new RLog(userDetails.getUsername(), "加入组："+user+"->"+role, new Timestamp(System.currentTimeMillis()), ""));
            enforcer.addRoleForUser(user.toLowerCase(), role.toLowerCase());
        }
    }

    @PostMapping(value = "/unbind")
    @ResponseBody
    public void unbind(@AuthenticationPrincipal UserDetails userDetails,
                       @RequestParam(value = "user") String user,
                       @RequestParam(value = "roles") List<String> roles){
        for (String role : roles) {
            rLogService.save(new RLog(userDetails.getUsername(), "退出组："+user+"<-"+role, new Timestamp(System.currentTimeMillis()), ""));
            enforcer.deleteRoleForUser(user.toLowerCase(), role.toLowerCase());
        }
    }

}
