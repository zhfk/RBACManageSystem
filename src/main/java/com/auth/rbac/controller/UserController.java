package com.auth.rbac.controller;

import com.auth.rbac.dao.ResponseData;
import com.auth.rbac.dao.User;
import com.auth.rbac.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/user/add")
    @ResponseBody
    public String addUser(@RequestParam(value = "username") String username,
                           @RequestParam(value = "organization") String organization,
                          @RequestParam(value = "email") String email,
                          @RequestParam(value = "gender") String gender,
                           @RequestParam(value = "description") String description){
        Optional<User> opUser = userService.getUserByUsername(username);
        if(opUser.isPresent()){
            return opUser.get().getUsername()+" 已经存在了!";
        }else{
            User user = new User();
            user.setUsername(username);
            user.setOrganization(organization);
            user.setEmail(email);
            user.setGender(gender);
            user.setDescription(description);
            userService.addUser(user);
            return "添加 " + username + " 成功!";
        }
    }

    @GetMapping(value = "/user/getPageUser")
    @ResponseBody
    public ResponseData getPageUser(@RequestParam(value = "page") Integer page,
                                         @RequestParam(value = "limit") Integer limit){
        long userNum = userService.getUserNum();
        Page<User> users = userService.getUserByPage(PageRequest.of(page - 1, limit));
        return new ResponseData<>(0, "succeed", userNum, users.getContent());
    }

    @DeleteMapping(value = "/user/delete")
    @ResponseBody
    public void deleteUser(@RequestParam(value = "id") long id){
        userService.deleteUser(id);
    }

    @DeleteMapping(value = "/user/batchDelete")
    @ResponseBody
    public void deleteUsers(@RequestParam(value = "id") String idList){
        userService.deleteUsers(idList);
    }

}
