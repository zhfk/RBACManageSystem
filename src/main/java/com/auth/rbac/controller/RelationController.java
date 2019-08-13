package com.auth.rbac.controller;

import com.auth.rbac.dao.Rtree;
import org.casbin.jcasbin.main.Enforcer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.templateparser.raw.RawTemplateParser;

import java.util.ArrayList;
import java.util.List;


@Controller
public class RelationController {

    private Logger logger = LoggerFactory.getLogger(RelationController.class);

    @Autowired
    private Enforcer enforcer;

    @GetMapping(value = "/relation/show")
    @ResponseBody
    public Rtree relationShip(@RequestParam(value = "subject") String subject){
        List<List<String>> permissions = null;
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