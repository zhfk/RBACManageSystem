package com.auth.rbac.controller;

import com.auth.rbac.dao.ResponseData;
import com.auth.rbac.dao.Privilege;
import com.auth.rbac.service.PrivilegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping(value = "/api/v1/privilege")
public class PrivilegeController {

    @Autowired
    private PrivilegeService privilegeService;

    @PostMapping(value = "/add")
    @ResponseBody
    public String addPrivilege(@RequestParam(value = "resource") String resource,
                               @RequestParam(value = "name") String name,
                               @RequestParam(value = "desc") String desc){
        Optional<Privilege> opPrivilege = privilegeService.getPrivilegeBynameAndResource(resource, name);
        if(opPrivilege.isPresent()){
            return opPrivilege.get().getName()+" 已经存在了!";
        }else{
            Privilege privilege = new Privilege();
            privilege.setResource(resource);
            privilege.setName(name);
            privilege.setDesc(desc);
            privilegeService.addPrivilege(privilege);
            return "添加 " + name + " 成功!";
        }
    }

    @PostMapping(value = "/modify")
    @ResponseBody
    public String modifyPrivilege(@RequestParam(value = "id") Integer id,
                                  @RequestParam(value = "resource") String resource,
                                 @RequestParam(value = "name") String name,
                                 @RequestParam(value = "desc") String desc){
        Optional<Privilege> opPrivilege = privilegeService.getPrivilegeBynameAndResource(resource, name);
        if(opPrivilege.isPresent() && !opPrivilege.get().getId().equals(id)){
            return opPrivilege.get().getName()+" 已经存在了!";
        }else{
            Privilege Privilege = privilegeService.getPrivilegeById(id);
            Privilege.setResource(resource);
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
    public void deletePrivilege(@RequestParam(value = "id") Integer id){
        privilegeService.deletePrivilege(id);
    }

    @DeleteMapping(value = "/batchDelete")
    @ResponseBody
    public void deletePrivileges(@RequestParam(value = "id") String idList){
        privilegeService.deletePrivileges(idList);
    }

}
