package com.auth.rbac.controller;

import com.auth.rbac.dao.ResponseData;
import com.auth.rbac.dao.Resource;
import com.auth.rbac.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping(value = "/api/v1/resource")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @PostMapping(value = "/add")
    @ResponseBody
    public String addResource(@RequestParam(value = "name") String name,
                           @RequestParam(value = "desc") String desc){
        Optional<Resource> opResource = resourceService.getResourceByname(name);
        if(opResource.isPresent()){
            return opResource.get().getName()+" 已经存在了!";
        }else{
            Resource resource = new Resource();
            resource.setName(name);
            resource.setDesc(desc);
            resourceService.addResource(resource);
            return "添加 " + name + " 成功!";
        }
    }

    @PostMapping(value = "/modify")
    @ResponseBody
    public String modifyResource(@RequestParam(value = "id") Integer id,
                          @RequestParam(value = "name") String name,
                          @RequestParam(value = "desc") String desc){
        Optional<Resource> opResource = resourceService.getResourceByname(name);
        if(opResource.isPresent() && !opResource.get().getId().equals(id)){
            return opResource.get().getName()+" 已经存在了!";
        }else{
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
    public void deleteResource(@RequestParam(value = "id") Integer id){
        resourceService.deleteResource(id);
    }

    @DeleteMapping(value = "/batchDelete")
    @ResponseBody
    public void deleteResources(@RequestParam(value = "id") String idList){
        resourceService.deleteResources(idList);
    }

}
