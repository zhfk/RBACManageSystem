package com.auth.rbac.service;

import com.auth.rbac.dao.Resource;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ResourceService {

    void addResource(Resource Resource);

    void deleteResource(Integer id);

    void deleteResources(String idListStr);

    Optional<Resource> getResourceByname(String name);

    Resource getResourceById(Integer id);

    Page<Resource> getResourceByPage(Integer page, Integer limit);

    Page<Resource> findBynameLike(Integer page, Integer limit, String username);

    List<Map<String, Object>> getAllname();
}
