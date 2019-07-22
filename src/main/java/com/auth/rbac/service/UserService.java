package com.auth.rbac.service;

import com.auth.rbac.dao.User;

public interface UserService {

    void addUser(User user);

    void deleteUser(Long id);

    void deleteUser(User user);

    void updateUser(User user);

    User findUserByUsername(String username);

    User findUserById(Long id);

}
