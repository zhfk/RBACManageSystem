package com.auth.rbac.controller;

import com.auth.rbac.dao.RLog;
import com.auth.rbac.dao.Rtree;
import com.auth.rbac.service.RLogService;
import org.casbin.jcasbin.main.Enforcer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Controller
@RequestMapping(value = "/api/v1/relation")
public class RelationController {

    private Logger logger = LoggerFactory.getLogger(RelationController.class);

    @Autowired
    private Enforcer enforcer;

    @Autowired
    private RLogService rLogService;

    @GetMapping(value = {"/",""})
    public String relation(){
        return "relation/relation";
    }

    @GetMapping(value = "/show")
    @ResponseBody
    public Rtree relationShip(@AuthenticationPrincipal UserDetails userDetails,
                              @RequestParam(value = "subject") String subject){
        rLogService.save(new RLog(userDetails.getUsername(), "查看"+subject+"关系", new Timestamp(System.currentTimeMillis()), ""));
        //获取用户permission
        List<List<String>> permissions = new ArrayList<>();
        try {
            permissions = enforcer.getImplicitPermissionsForUser(subject);
        }catch (Exception e){
            logger.warn(e.getMessage());
            try{
                permissions = enforcer.getPermissionsForUser(subject);
            }catch (Exception e1){
                logger.warn(e1.getMessage());
            }
        }
        //获取用户的组信息
        List<String> roles = new ArrayList<>();
        try{
            roles = enforcer.getRolesForUser(subject);
        }catch (Exception e){
            logger.warn(e.getMessage());
        }

        //合并组和permission
        List<String> pRoles = permissions.stream().filter( list -> !list.get(0).equals(subject)).map(list -> list.get(0)).collect(Collectors.toList());
        if(!CollectionUtils.isEmpty(roles)){
            for (String role : roles) {
                if(!pRoles.contains(role)){
                    permissions.add(Collections.singletonList(role));
                }
            }
        }

        Rtree root = new Rtree();
        root.setName(subject);
        root.setChildren(new ArrayList<>());
        for (List<String> permission : permissions) {
            buildRtree(root, permission);
        }
        return root;
    }

    private Rtree buildRtree(Rtree root, List<String> data){
        if(CollectionUtils.isEmpty(data)) return null;
        String name = data.get(0);
        if(isRootOrChild(name, root) != null){
            Rtree child = isRootOrChild(name, root);
            List<String> newData = new ArrayList<>(data.subList(1, data.size()));
            return buildRtree(child, newData);
        }else{
            Rtree node = new Rtree();
            node.setName(name);
            node.setChildren(new ArrayList<>());
            root.getChildren().add(node);
            List<String> newData = new ArrayList<>(data.subList(1, data.size()));
            return buildRtree(node, newData);
        }
    }

    private Rtree isRootOrChild(String name, Rtree root){
        if(name.equals(root.getName())) return root;
        for (Rtree child : root.getChildren()) {
            if (child.getName().equals(name)){
                return child;
            }
        }
        return null;
    }
}
