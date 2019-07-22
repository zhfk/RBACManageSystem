package com.auth.rbac.service;

import com.auth.rbac.dao.Admin;

import java.util.List;

public interface AdminService {

    Boolean update(Admin admin);

    Admin getAdminInfo();

}
