package com.auth.rbac.service.Impl;

import com.auth.rbac.dao.Privilege;
import com.auth.rbac.repository.PrivilegeRepository;
import com.auth.rbac.service.PrivilegeService;
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
public class PrivilegeServiceImpl implements PrivilegeService {

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Override
    public void addPrivilege(Privilege Privilege) {
        privilegeRepository.save(Privilege);
    }

    @Override
    public void deletePrivilege(Integer id) {
        privilegeRepository.deleteById(id);
    }

    @Override
    public void deletePrivileges(String idListStr) {
        List<Integer> idList = Arrays.stream(idListStr.split(",")).map(Integer::valueOf).collect(Collectors.toList());
        privilegeRepository.deletePrivileges(idList);
    }

    @Override
    public Optional<Privilege> getPrivilegeByname(String name) {
        return privilegeRepository.findPrivilegeByName(name);
    }

    @Override
    public Privilege getPrivilegeById(Integer id) {
        Privilege Privilege = null;
        Optional<Privilege> optionalPrivilege = privilegeRepository.findById(id);
        if (optionalPrivilege.isPresent()) {
            Privilege = optionalPrivilege.get();
        }
        return Privilege;
    }

    @Override
    public Page<Privilege> getPrivilegeByPage(Integer page, Integer limit) {
        return privilegeRepository.findAll(PageRequest.of(page, limit));
    }

    @Override
    public Page<Privilege> findBynameLike(Integer page, Integer limit, String name) {
        Specification<Privilege> spec = new Specification<Privilege>() {
            @Override
            public Predicate toPredicate(Root<Privilege> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> pr = new ArrayList<>();
                if (!StringUtils.isEmpty(name)) {
                    pr.add(criteriaBuilder.like(root.get("name").as(String.class), "%" + name + "%"));
                }
                return criteriaBuilder.and(pr.toArray(new Predicate[0]));
            }
        };
        return privilegeRepository.findAll(spec, PageRequest.of(page, limit, new Sort(Sort.Direction.DESC, "id")));
    }

    @Override
    public List<Map<String, Object>> getAllname() {
        return privilegeRepository.findAllName();
    }
}
