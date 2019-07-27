package com.auth.rbac.service;

import com.auth.rbac.dao.Role;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface RoleService {

    void addRole(Role role);

    void deleteRole(Integer id);

    void deleteRoles(String idListStr);

    Optional<Role> getRoleByname(String name);

    Role getRoleById(Integer id);

    Page<Role> getRoleByPage(Integer page, Integer limit);

    Page<Role> findBynameLike(Integer page, Integer limit, String name);

    List<Map<String, Object>> getAllname();
}
