package com.auth.rbac.controller;

import com.auth.rbac.dao.RLog;
import com.auth.rbac.dao.ResponseData;
import com.auth.rbac.dao.User;
import com.auth.rbac.service.RLogService;
import com.auth.rbac.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Optional;

@Controller
@RequestMapping(value = "/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RLogService rLogService;

    @PostMapping(value = "/add")
    @ResponseBody
    public String addUser(@AuthenticationPrincipal UserDetails userDetails,
                          @RequestParam(value = "username") String username,
                          @RequestParam(value = "organization") String organization,
                          @RequestParam(value = "email") String email,
                          @RequestParam(value = "gender") String gender,
                          @RequestParam(value = "description") String description){
        Optional<User> opUser = userService.getUserByUsername(username);
        if(opUser.isPresent()){
            return opUser.get().getUsername()+" 已经存在了!";
        }else{
            rLogService.save(new RLog(userDetails.getUsername(), "添加用户："+username, new Timestamp(System.currentTimeMillis()), ""));
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
    public String modifyUser(@AuthenticationPrincipal UserDetails userDetails,
                             @RequestParam(value = "id") Integer id,
                          @RequestParam(value = "username") String username,
                          @RequestParam(value = "organization") String organization,
                          @RequestParam(value = "email") String email,
                          @RequestParam(value = "gender") String gender,
                          @RequestParam(value = "description") String description){
        Optional<User> opUser = userService.getUserByUsername(username);
        if(opUser.isPresent() && !opUser.get().getId().equals(id)){
            return opUser.get().getUsername()+" 已经存在了!";
        }else {
            rLogService.save(new RLog(userDetails.getUsername(), "修改用户："+username, new Timestamp(System.currentTimeMillis()), ""));
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
    public void deleteUser(@AuthenticationPrincipal UserDetails userDetails,
                           @RequestParam(value = "id") Integer id,
                           @RequestParam(value = "name") String name){
        rLogService.save(new RLog(userDetails.getUsername(), "批量删除用户："+name, new Timestamp(System.currentTimeMillis()), ""));
        userService.deleteUser(id);
    }

    @DeleteMapping(value = "/batchDelete")
    @ResponseBody
    public void deleteUsers(@AuthenticationPrincipal UserDetails userDetails,
                            @RequestParam(value = "id") String idList,
                            @RequestParam(value = "name") String name){
        rLogService.save(new RLog(userDetails.getUsername(), "批量删除用户："+name, new Timestamp(System.currentTimeMillis()), ""));
        userService.deleteUsers(idList);
    }


}
