package com.auth.rbac.service.Impl;

import com.auth.rbac.service.GrantService;
import org.casbin.jcasbin.main.Enforcer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GrantServiceImpl implements GrantService {
    @Autowired
    private Enforcer enforcer;

    public List<List<String>> getPermission(String user, String resource) {
        return enforcer.getFilteredPolicy(0, user, resource);
    }

    @Override
    public List<String> getRole(String user) {
        List<String> res = enforcer.getRolesForUser(user);
        return res == null ? new ArrayList<>() : res;
    }

}
