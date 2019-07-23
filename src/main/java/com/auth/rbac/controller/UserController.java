package com.auth.rbac.controller;

import com.auth.rbac.dao.ResponseData;
import com.auth.rbac.dao.User;
import com.auth.rbac.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/user/add")
    public String addUser(@RequestParam(value = "username") String username,
                           @RequestParam(value = "organization") String organization,
                          @RequestParam(value = "email") String email,
                          @RequestParam(value = "gender") String gender,
                           @RequestParam(value = "description") String description){
        User user = new User();
        user.setUsername(username);
        user.setOrganization(organization);
        user.setEmail(email);
        user.setGender(gender);
        user.setDescription(description);
        userService.addUser(user);
        return "object_manage/user_manage";
    }

    @GetMapping(value = "/user/getPageUser")
    @ResponseBody
    public ResponseData getPageUser(@RequestParam(value = "page") Integer page,
                                         @RequestParam(value = "limit") Integer limit){
        long userNum = userService.getUserNum();
        Page<User> users = userService.getUserByPage(PageRequest.of(page - 1, limit));
        return new ResponseData<>(0, "succeed", userNum, users.getContent());
    }

}
