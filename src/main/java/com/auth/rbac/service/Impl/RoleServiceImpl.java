package com.auth.rbac.service.Impl;

import com.auth.rbac.dao.Role;
import com.auth.rbac.repository.RoleRepository;
import com.auth.rbac.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void addRole(Role Role) {
        roleRepository.save(Role);
    }

    @Override
    public void deleteRole(Integer id) {
        roleRepository.deleteById(id);
    }

    @Override
    public void deleteRoles(String idListStr) {
        List<Integer> idList = Arrays.stream(idListStr.split(",")).map(Integer::valueOf).collect(Collectors.toList());
        roleRepository.deleteRoles(idList);
    }

    @Override
    public Optional<Role> getRoleByname(String name) {
        return roleRepository.findRoleByName(name);
    }

    @Override
    public Role getRoleById(Integer id) {
        Role Role = null;
        Optional<Role> optionalRole = roleRepository.findById(id);
        if (optionalRole.isPresent()) {
            Role = optionalRole.get();
        }
        return Role;
    }

    @Override
    public Page<Role> getRoleByPage(Integer page, Integer limit) {
        return roleRepository.findAll(PageRequest.of(page, limit));
    }

    @Override
    public Page<Role> findBynameLike(Integer page, Integer limit, String name) {
        Specification<Role> spec = new Specification<Role>() {
            @Override
            public Predicate toPredicate(Root<Role> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> pr = new ArrayList<>();
                if (!StringUtils.isEmpty(name)) {
                    pr.add(criteriaBuilder.like(root.get("name").as(String.class), "%" + name + "%"));
                }
                return criteriaBuilder.and(pr.toArray(new Predicate[0]));
            }
        };
        return roleRepository.findAll(spec, PageRequest.of(page, limit, new Sort(Sort.Direction.DESC, "id")));
    }

    @Override
    public List<Map<String, Object>> getAllname() {
        return roleRepository.findAllName();
    }
}
