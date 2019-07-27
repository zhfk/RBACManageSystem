package com.auth.rbac.controller;

import com.auth.rbac.dao.Privilege;
import com.auth.rbac.dao.Resource;
import com.auth.rbac.dao.Role;
import com.auth.rbac.dao.User;
import com.auth.rbac.service.PrivilegeService;
import com.auth.rbac.service.ResourceService;
import com.auth.rbac.service.RoleService;
import com.auth.rbac.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Api("swagger ui 注释 用户管理")
@Controller
public class ObjectManageController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private PrivilegeService privilegeService;


    /**
     * user
     *
     * @return
     */
    @GetMapping(value = "/manage/user")
    public String userManage() {
        return "object_manage/user_manage";
    }

    @GetMapping(value = "/manage/relation/user")
    public String manageRelationUser(@RequestParam(value = "userId") String userId, ModelMap modelMap) {
        modelMap.addAttribute("userId", userId);
        return "relation/user_relation_iframe";
    }

    @GetMapping(value = "/manage/add/user")
    public String manageAddUser() {
        return "user/add_user";
    }

    @GetMapping(value = "/manage/user/info")
    public String manageUserInfo(@RequestParam(value = "userId") Integer userId, ModelMap modelMap) {
        User user = userService.getUserById(userId);
        modelMap.addAttribute("user", user);
        return "user/user_info";
    }

    /**
     * role
     *
     * @return
     */
    @GetMapping(value = "/manage/role")
    public String roleManage() {
        return "object_manage/role_manage";
    }

    @GetMapping(value = "/manage/add/role")
    public String manageAddRole() {
        return "role/add_role";
    }

    @GetMapping(value = "/manage/role/info")
    public String manageRoleInfo(@RequestParam(value = "id") int id, ModelMap modelMap) {
        Role role = roleService.getRoleById(id);
        modelMap.addAttribute("role", role);
        return "role/role_info";
    }

    /**
     * resource
     */
    @GetMapping(value = "/manage/resource")
    public String resourceManage() {
        return "object_manage/resource_manage";
    }

    @GetMapping(value = "/manage/add/resource")
    public String manageAddResource() {
        return "resource/add_resource";
    }

    @GetMapping(value = "/manage/resource/info")
    public String manageResourceInfo(@RequestParam(value = "id") int id, ModelMap modelMap) {
        Resource resource = resourceService.getResourceById(id);
        modelMap.addAttribute("resource", resource);
        return "resource/resource_info";
    }

    /**
     * privilege
     */
    @GetMapping(value = "/manage/privilege")
    public String privilegeManage() {
        return "object_manage/privilege_manage";
    }

    @GetMapping(value = "/manage/add/privilege")
    public String manageAddPrivilege() {
        return "privilege/add_privilege";
    }

    @GetMapping(value = "/manage/privilege/info")
    public String managePrivilegeInfo(@RequestParam(value = "id") int id, ModelMap modelMap) {
        Privilege privilege = privilegeService.getPrivilegeById(id);
        modelMap.addAttribute("privilege", privilege);
        return "privilege/privilege_info";
    }

}
