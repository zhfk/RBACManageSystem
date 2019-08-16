package com.auth.rbac.service.Impl;

import com.auth.rbac.repository.ResourceBindPrivilegeRepository;
import com.auth.rbac.service.ResourceBindPrivilegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResourceBindPrivilegeServiceImpl implements ResourceBindPrivilegeService {

    @Autowired
    private ResourceBindPrivilegeRepository resourceBindPrivilegeRepository;

    @Override
    public void bind(String resource, String privilege) {
        resourceBindPrivilegeRepository.insert(resource, privilege);
    }

    @Override
    public void unbind(String resource, String privilege) {
        resourceBindPrivilegeRepository.delete(resource, privilege);
    }

    @Override
    public List<String> getBindedPrivileges(String resource) {
        return resourceBindPrivilegeRepository.findAllPrivilege(resource);
    }
}
