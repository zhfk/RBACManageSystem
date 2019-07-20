package com.auth.rbac.service;

import com.auth.rbac.dao.User;

import java.util.List;

public interface UserService {

    void addUser(User user);
    List<User> getAllUser();

}
