package com.auth.rbac.service;

import com.auth.rbac.dao.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    void addUser(User user);

    void deleteUser(Long id);

    void deleteUser(User user);

    void updateUser(User user);

    User getUserByUsername(String username);

    User getUserById(Long id);

    Page<User> getUserByPage(Pageable pageable);

    long getUserNum();

}
