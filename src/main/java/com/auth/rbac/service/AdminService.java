package com.auth.rbac.service;

import com.auth.rbac.dao.Admin;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface AdminService extends UserDetailsService {

    Boolean update(Admin admin);

    Admin getAdminInfo();

}
