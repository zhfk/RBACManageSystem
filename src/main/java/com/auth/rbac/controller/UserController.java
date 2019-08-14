package com.auth.rbac.controller;

import com.auth.rbac.dao.ResponseData;
import com.auth.rbac.dao.User;
import com.auth.rbac.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping(value = "/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/add")
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

    @PostMapping(value = "/modify")
    @ResponseBody
    public String modifyUser(@RequestParam(value = "id") Integer id,
                          @RequestParam(value = "username") String username,
                          @RequestParam(value = "organization") String organization,
                          @RequestParam(value = "email") String email,
                          @RequestParam(value = "gender") String gender,
                          @RequestParam(value = "description") String description){
        Optional<User> opUser = userService.getUserByUsername(username);
        if(opUser.isPresent() && !opUser.get().getId().equals(id)){
            return opUser.get().getUsername()+" 已经存在了!";
        }else {
            User user = userService.getUserById(id);
            user.setUsername(username);
            user.setOrganization(organization);
            user.setEmail(email);
            user.setGender(gender);
            user.setDescription(description);
            userService.addUser(user);
            return "修改 " + username + " 成功!";
        }
    }

    @GetMapping(value = "/getPageUser")
    @ResponseBody
    public ResponseData getPageUser(@RequestParam(value = "page") Integer page,
                                         @RequestParam(value = "limit") Integer limit){
        Page<User> users = userService.getUserByPage(page-1, limit);
        return new ResponseData<>(0, "succeed", users.getTotalElements(), users.getContent());
    }

    @GetMapping(value = "/search")
    @ResponseBody
    public ResponseData search(@RequestParam(value = "page") Integer page,
                               @RequestParam(value = "limit") Integer limit,
                               @RequestParam(value = "username") String username){
        Page<User> users = userService.findByUsernameLike(page-1, limit, username);
        return new ResponseData<>(0, "succeed", users.getTotalElements(), users.getContent());
    }

    @DeleteMapping(value = "/delete")
    @ResponseBody
    public void deleteUser(@RequestParam(value = "id") Integer id){
        userService.deleteUser(id);
    }

    @DeleteMapping(value = "/batchDelete")
    @ResponseBody
    public void deleteUsers(@RequestParam(value = "id") String idList){
        userService.deleteUsers(idList);
    }


}
