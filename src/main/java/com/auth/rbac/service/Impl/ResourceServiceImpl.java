package com.auth.rbac.service.Impl;

import com.auth.rbac.dao.Resource;
import com.auth.rbac.repository.ResourceRepository;
import com.auth.rbac.service.ResourceService;
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
public class ResourceServiceImpl implements ResourceService {

    @Autowired
    private ResourceRepository resourceRepository;

    @Override
    public void addResource(Resource Resource) {
        resourceRepository.save(Resource);
    }

    @Override
    public void deleteResource(Integer id) {
        resourceRepository.deleteById(id);
    }

    @Override
    public void deleteResources(String idListStr) {
        List<Integer> idList = Arrays.stream(idListStr.split(",")).map(Integer::valueOf).collect(Collectors.toList());
        resourceRepository.deleteResources(idList);
    }

    @Override
    public Optional<Resource> getResourceByname(String name) {
        return resourceRepository.findResourceByName(name);
    }

    @Override
    public Resource getResourceById(Integer id) {
        Resource Resource = null;
        Optional<Resource> optionalResource = resourceRepository.findById(id);
        if (optionalResource.isPresent()) {
            Resource = optionalResource.get();
        }
        return Resource;
    }

    @Override
    public Page<Resource> getResourceByPage(Integer page, Integer limit) {
        return resourceRepository.findAll(PageRequest.of(page, limit));
    }

    @Override
    public Page<Resource> findBynameLike(Integer page, Integer limit, String name) {
        Specification<Resource> spec = new Specification<Resource>() {
            @Override
            public Predicate toPredicate(Root<Resource> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> pr = new ArrayList<>();
                if (!StringUtils.isEmpty(name)) {
                    pr.add(criteriaBuilder.like(root.get("name").as(String.class), "%" + name + "%"));
                }
                return criteriaBuilder.and(pr.toArray(new Predicate[0]));
            }
        };
        return resourceRepository.findAll(spec, PageRequest.of(page, limit, new Sort(Sort.Direction.DESC, "id")));
    }

    @Override
    public List<Map<String, Object>> getAllname() {
        return resourceRepository.findAllName();
    }
}
