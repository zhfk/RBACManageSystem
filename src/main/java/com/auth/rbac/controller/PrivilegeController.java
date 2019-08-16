package com.auth.rbac.controller;

import com.auth.rbac.dao.RLog;
import com.auth.rbac.dao.ResponseData;
import com.auth.rbac.dao.Privilege;
import com.auth.rbac.service.PrivilegeService;
import com.auth.rbac.service.RLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Optional;

@Controller
@RequestMapping(value = "/api/v1/privilege")
public class PrivilegeController {

    @Autowired
    private PrivilegeService privilegeService;

    @Autowired
    private RLogService rLogService;

    @PostMapping(value = "/add")
    @ResponseBody
    public String addPrivilege(@AuthenticationPrincipal UserDetails userDetails,
                               @RequestParam(value = "name") String name,
                               @RequestParam(value = "desc") String desc){
        Optional<Privilege> opPrivilege = privilegeService.getPrivilegeByName(name);
        if(opPrivilege.isPresent()){
            return opPrivilege.get().getName()+" 已经存在了!";
        }else{
            rLogService.save(new RLog(userDetails.getUsername(), "添加权限："+name, new Timestamp(System.currentTimeMillis()), ""));
            Privilege privilege = new Privilege();
            privilege.setName(name);
            privilege.setDesc(desc);
            privilegeService.addPrivilege(privilege);
            return "添加 " + name + " 成功!";
        }
    }

    @PostMapping(value = "/modify")
    @ResponseBody
    public String modifyPrivilege(@AuthenticationPrincipal UserDetails userDetails,
                                  @RequestParam(value = "id") Integer id,
                                  @RequestParam(value = "name") String name,
                                  @RequestParam(value = "desc") String desc){
        Optional<Privilege> opPrivilege = privilegeService.getPrivilegeByName(name);
        if(opPrivilege.isPresent() && !opPrivilege.get().getId().equals(id)){
            return opPrivilege.get().getName()+" 已经存在了!";
        }else{
            rLogService.save(new RLog(userDetails.getUsername(), "修改权限："+name, new Timestamp(System.currentTimeMillis()), ""));
            Privilege Privilege = privilegeService.getPrivilegeById(id);
            Privilege.setName(name);
            Privilege.setDesc(desc);
            privilegeService.addPrivilege(Privilege);
            return "修改 " + name + " 成功!";
        }
    }

    @GetMapping(value = "/getPagePrivilege")
    @ResponseBody
    public ResponseData getPagePrivilege(@RequestParam(value = "page") Integer page,
                                        @RequestParam(value = "limit") Integer limit){
        Page<Privilege> Privileges = privilegeService.getPrivilegeByPage(page-1, limit);
        return new ResponseData<>(0, "succeed", Privileges.getTotalElements(), Privileges.getContent());
    }

    @GetMapping(value = "/search")
    @ResponseBody
    public ResponseData search(@RequestParam(value = "page") Integer page,
                               @RequestParam(value = "limit") Integer limit,
                               @RequestParam(value = "name") String name){
        Page<Privilege> privileges = privilegeService.findBynameLike(page-1, limit, name);
        return new ResponseData<>(0, "succeed", privileges.getTotalElements(), privileges.getContent());
    }

    @DeleteMapping(value = "/delete")
    @ResponseBody
    public void deletePrivilege(@AuthenticationPrincipal UserDetails userDetails,
                                @RequestParam(value = "id") Integer id,
                                @RequestParam(value = "name") String name){
        rLogService.save(new RLog(userDetails.getUsername(), "删除权限："+name, new Timestamp(System.currentTimeMillis()), ""));
        privilegeService.deletePrivilege(id);
    }

    @DeleteMapping(value = "/batchDelete")
    @ResponseBody
    public void deletePrivileges(@AuthenticationPrincipal UserDetails userDetails,
                                 @RequestParam(value = "id") String idList,
                                 @RequestParam(value = "name") String names){
        rLogService.save(new RLog(userDetails.getUsername(), "批量删除权限："+names, new Timestamp(System.currentTimeMillis()), ""));
        privilegeService.deletePrivileges(idList);
    }

}
