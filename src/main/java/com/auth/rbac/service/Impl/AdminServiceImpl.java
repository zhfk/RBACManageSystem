package com.auth.rbac.service.Impl;

import com.auth.rbac.dao.Admin;
import com.auth.rbac.repository.AdminRepository;
import com.auth.rbac.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public Boolean update(Admin admin) {
        boolean succeed = false;
        if ("admin".equals(admin.getUsername())) {
            admin.setId(1L);
            adminRepository.save(admin);
            succeed = true;
        }
        return succeed;
    }

    @Override
    public Admin getAdminInfo() {
        Admin admin = null;
        Optional<Admin> optionalAdmin = adminRepository.findById(1L);
        if(optionalAdmin.isPresent()){
            admin = optionalAdmin.get();
        }else{
            admin = new Admin();
            admin.setUsername("admin");
            admin.setPassword("admin");
        }
        return admin;
    }
}
