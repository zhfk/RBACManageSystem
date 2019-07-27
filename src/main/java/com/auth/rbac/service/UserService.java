package com.auth.rbac.service;

import com.auth.rbac.dao.User;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserService {

    void addUser(User user);

    void deleteUser(Integer id);

    void deleteUsers(String idListStr);

    Optional<User> getUserByUsername(String username);

    User getUserById(Integer id);

    Page<User> getUserByPage(Integer page, Integer limit);

    Page<User> findByUsernameLike(Integer page, Integer limit, String username);

    List<Map<String, Object>> getAllUsername();
}
