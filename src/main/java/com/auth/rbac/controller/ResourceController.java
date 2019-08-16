package com.auth.rbac.controller;

import com.auth.rbac.dao.RLog;
import com.auth.rbac.dao.ResponseData;
import com.auth.rbac.dao.Resource;
import com.auth.rbac.service.PrivilegeService;
import com.auth.rbac.service.RLogService;
import com.auth.rbac.service.ResourceBindPrivilegeService;
import com.auth.rbac.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/api/v1/resource")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private PrivilegeService privilegeService;

    @Autowired
    private RLogService rLogService;

    @Autowired
    private ResourceBindPrivilegeService resourceBindPrivilegeService;

    @PostMapping(value = "/add")
    @ResponseBody
    public String addResource(@AuthenticationPrincipal UserDetails userDetails,
                              @RequestParam(value = "name") String name,
                              @RequestParam(value = "desc") String desc){
        Optional<Resource> opResource = resourceService.getResourceByname(name);
        if(opResource.isPresent()){
            return opResource.get().getName()+" 已经存在了!";
        }else{
            rLogService.save(new RLog(userDetails.getUsername(), "添加资源："+name, new Timestamp(System.currentTimeMillis()), ""));
            Resource resource = new Resource();
            resource.setName(name);
            resource.setDesc(desc);
            resourceService.addResource(resource);
            return "添加 " + name + " 成功!";
        }
    }

    @PostMapping(value = "/modify")
    @ResponseBody
    public String modifyResource(@AuthenticationPrincipal UserDetails userDetails,
                                 @RequestParam(value = "id") Integer id,
                                 @RequestParam(value = "name") String name,
                                 @RequestParam(value = "desc") String desc){
        Optional<Resource> opResource = resourceService.getResourceByname(name);
        if(opResource.isPresent() && !opResource.get().getId().equals(id)){
            return opResource.get().getName()+" 已经存在了!";
        }else{
            rLogService.save(new RLog(userDetails.getUsername(), "修改资源："+name, new Timestamp(System.currentTimeMillis()), ""));
            Resource Resource = resourceService.getResourceById(id);
            Resource.setName(name);
            Resource.setDesc(desc);
            resourceService.addResource(Resource);
            return "修改 " + name + " 成功!";
        }
    }

    @GetMapping(value = "/getPageResource")
    @ResponseBody
    public ResponseData getPageResource(@RequestParam(value = "page") Integer page,
                                         @RequestParam(value = "limit") Integer limit){
        Page<Resource> Resources = resourceService.getResourceByPage(page-1, limit);
        return new ResponseData<>(0, "succeed", Resources.getTotalElements(), Resources.getContent());
    }

    @GetMapping(value = "/search")
    @ResponseBody
    public ResponseData search(@RequestParam(value = "page") Integer page,
                               @RequestParam(value = "limit") Integer limit,
                               @RequestParam(value = "name") String name){
        Page<Resource> resources = resourceService.findBynameLike(page-1, limit, name);
        return new ResponseData<>(0, "succeed", resources.getTotalElements(), resources.getContent());
    }

    @DeleteMapping(value = "/delete")
    @ResponseBody
    public void deleteResource(@AuthenticationPrincipal UserDetails userDetails,
                               @RequestParam(value = "id") Integer id,
                               @RequestParam(value = "name") String name){
        rLogService.save(new RLog(userDetails.getUsername(), "删除资源："+name, new Timestamp(System.currentTimeMillis()), ""));
        resourceService.deleteResource(id);
    }

    @DeleteMapping(value = "/batchDelete")
    @ResponseBody
    public void deleteResources(@AuthenticationPrincipal UserDetails userDetails,
                                @RequestParam(value = "id") String idList,
                                @RequestParam(value = "name") String names){
        rLogService.save(new RLog(userDetails.getUsername(), "批量资源："+names, new Timestamp(System.currentTimeMillis()), ""));
        resourceService.deleteResources(idList);
    }

    @PostMapping(value = "/bind")
    @ResponseBody
    public void bind(@AuthenticationPrincipal UserDetails userDetails,
                     @RequestParam(value = "resource") String resource,
                     @RequestParam(value = "privileges") List<String> privileges){
        for (String privilege : privileges) {
            rLogService.save(new RLog(userDetails.getUsername(), "分配权限："+resource+"<-"+privilege, new Timestamp(System.currentTimeMillis()), ""));
            resourceBindPrivilegeService.bind(resource.toLowerCase(), privilege.toLowerCase());
        }
    }

    @PostMapping(value = "/unbind")
    @ResponseBody
    public void unbind(@AuthenticationPrincipal UserDetails userDetails,
                       @RequestParam(value = "resource") String resource,
                       @RequestParam(value = "privileges") List<String> privileges){
        for (String privilege : privileges) {
            rLogService.save(new RLog(userDetails.getUsername(), "撤销权限："+resource+"<-"+privilege, new Timestamp(System.currentTimeMillis()), ""));
            resourceBindPrivilegeService.unbind(resource.toLowerCase(), privilege.toLowerCase());
        }
    }

    @GetMapping(value = "/privilege")
    @ResponseBody
    public Map<String, List<String>> bindedPrivileges(@RequestParam(value = "resource") String resource){
        Map<String, List<String>> bindings = new HashMap<>();
        List<String> binded = resourceBindPrivilegeService.getBindedPrivileges(resource);
        List<Map<String, Object>> privilegs = privilegeService.getAllname();
        List<String> all = privilegs.stream().map(p -> (String)p.get("name")).collect(Collectors.toList());
        bindings.put("binded", binded);
        bindings.put("all", all);
        return bindings;
    }

}
