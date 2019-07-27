package com.auth.rbac.service.Impl;

import com.auth.rbac.dao.User;
import com.auth.rbac.repository.UserRepository;
import com.auth.rbac.service.UserService;
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
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void addUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }

    @Override
    public void deleteUsers(String idListStr) {
        java.util.List<Integer> idList = Arrays.stream(idListStr.split(",")).map(Integer::valueOf).collect(Collectors.toList());
        userRepository.deleteUsers(idList);
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public User getUserById(Integer id) {
        User user = null;
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
        }
        return user;
    }

    @Override
    public Page<User> getUserByPage(Integer page, Integer limit) {
        return userRepository.findAll(PageRequest.of(page, limit));
    }

    @Override
    public Page<User> findByUsernameLike(Integer page, Integer limit, String username) {
        Specification<User> spec = new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> pr = new ArrayList<>();
                if (!StringUtils.isEmpty(username)) {
                    pr.add(criteriaBuilder.like(root.get("username").as(String.class), "%" + username + "%"));
                }
                return criteriaBuilder.and(pr.toArray(new Predicate[0]));
            }
        };
        return userRepository.findAll(spec, PageRequest.of(page, limit, new Sort(Sort.Direction.DESC, "id")));
    }

    @Override
    public List<Map<String, Object>> getAllUsername() {
        return userRepository.findAllUsername();
    }
}
