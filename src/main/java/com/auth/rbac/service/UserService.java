package com.auth.rbac.service;

import com.auth.rbac.dao.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserService {

    void addUser(User user);

    void saveUser(User user);

    void deleteUser(Long id);

    void deleteUser(User user);

    void deleteUsers(String id);

    void updateUser(User user);

    Optional<User> getUserByUsername(String username);

    User getUserById(Long id);

    Page<User> getUserByPage(Integer page, Integer limit);

    long getUserNum();

    Page<User> findByUsernameLike(Integer page, Integer limit, String username);
}
