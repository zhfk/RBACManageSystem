package com.auth.rbac.controller;

import com.auth.rbac.dao.User;
import com.auth.rbac.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/user/add")
    public String addUser(@RequestParam(value = "username") String username,
                           @RequestParam(value = "organization") String organization,
                           @RequestParam(value = "description") String description){
        User user = new User();
        user.setUsername(username);
        user.setOrganization(organization);
        user.setDescription(description);
        userService.addUser(user);
        return "object_manage/user_manage";
    }

}
