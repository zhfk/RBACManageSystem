package com.auth.rbac.controller;

import com.auth.rbac.dao.Privilege;
import com.auth.rbac.dao.Resource;
import com.auth.rbac.dao.Role;
import com.auth.rbac.dao.User;
import com.auth.rbac.service.PrivilegeService;
import com.auth.rbac.service.ResourceService;
import com.auth.rbac.service.RoleService;
import com.auth.rbac.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping(value = "/api/v1/manage")
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
    @GetMapping(value = "/user")
    public String userManage() {
        return "object_manage/user_manage";
    }

    @GetMapping(value = "/relation/user")
    public String manageRelationUser(@RequestParam(value = "userId") String userId, ModelMap modelMap) {
        modelMap.addAttribute("userId", userId);
        return "relation/user_relation_iframe";
    }

    @GetMapping(value = "/add/user")
    public String manageAddUser() {
        return "user/add_user";
    }

    @GetMapping(value = "/user/info")
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
    @GetMapping(value = "/role")
    public String roleManage() {
        return "object_manage/role_manage";
    }

    @GetMapping(value = "/add/role")
    public String manageAddRole() {
        return "role/add_role";
    }

    @GetMapping(value = "/role/info")
    public String manageRoleInfo(@RequestParam(value = "id") int id, ModelMap modelMap) {
        Role role = roleService.getRoleById(id);
        modelMap.addAttribute("role", role);
        return "role/role_info";
    }

    /**
     * resource
     */
    @GetMapping(value = "/resource")
    public String resourceManage() {
        return "object_manage/resource_manage";
    }

    @GetMapping(value = "/add/resource")
    public String manageAddResource() {
        return "resource/add_resource";
    }

    @GetMapping(value = "/resource/info")
    public String manageResourceInfo(@RequestParam(value = "id") int id, ModelMap modelMap) {
        Resource resource = resourceService.getResourceById(id);
        modelMap.addAttribute("resource", resource);
        return "resource/resource_info";
    }

    /**
     * privilege
     */
    @GetMapping(value = "/privilege")
    public String privilegeManage() {
        return "object_manage/privilege_manage";
    }

    @GetMapping(value = "/add/privilege")
    public String manageAddPrivilege(ModelMap modelMap) {
        List<String> resources = resourceService.getNames();
        modelMap.addAttribute("resources", resources);
        return "privilege/add_privilege";
    }

    @GetMapping(value = "/privilege/info")
    public String managePrivilegeInfo(@RequestParam(value = "id") int id, ModelMap modelMap) {
        Privilege privilege = privilegeService.getPrivilegeById(id);
        List<String> resources = resourceService.getNames();
        modelMap.addAttribute("privilege", privilege);
        modelMap.addAttribute("resources", resources);
        modelMap.addAttribute("curres", privilege.getResource());
        return "privilege/privilege_info";
    }

}
