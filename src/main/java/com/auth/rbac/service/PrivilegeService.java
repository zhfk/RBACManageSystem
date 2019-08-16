package com.auth.rbac.service;

import com.auth.rbac.dao.Privilege;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface PrivilegeService {

    void addPrivilege(Privilege Privilege);

    void deletePrivilege(Integer id);

    void deletePrivileges(String idListStr);

    Privilege getPrivilegeById(Integer id);

    Page<Privilege> getPrivilegeByPage(Integer page, Integer limit);

    Page<Privilege> findBynameLike(Integer page, Integer limit, String username);

    List<Map<String, Object>> getAllname();

    Optional<Privilege> getPrivilegeByName(String name);
}
