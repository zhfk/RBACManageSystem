package com.auth.rbac.service.Impl;

import com.auth.rbac.dao.Admin;
import com.auth.rbac.repository.AdminRepository;
import com.auth.rbac.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

    private String hardCode = "winteriscoming";

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public Boolean update(Admin admin) {
        boolean succeed = false;
        if ("admin".equals(admin.getUsername())) {
            admin.setId(1L);
            admin.setPassword(new BCryptPasswordEncoder().encode(hardCode));
            admin.setRoles("ADMIN");
            adminRepository.save(admin);
            succeed = true;
        }
        return succeed;
    }

    @Override
    public Admin getAdminInfo() {
        Admin admin = null;
        Optional<Admin> optionalAdmin = adminRepository.findById(1L);
        if (optionalAdmin.isPresent()) {
            admin = optionalAdmin.get();
        } else {
            admin = new Admin();
            admin.setUsername("admin");
            admin.setPassword(new BCryptPasswordEncoder().encode(hardCode));
            admin.setRoles("ADMIN");
        }
        return admin;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (StringUtils.isEmpty(username)) {
            throw new UsernameNotFoundException("用户名为空");
        }
        Optional<Admin> opAdmin = adminRepository.findByUsername(username);
        if (!opAdmin.isPresent()) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        Admin admin = opAdmin.get();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        Arrays.stream(admin.getRoles().split(","))
                .allMatch(role -> authorities.add(new SimpleGrantedAuthority(role)));
        return new User(admin.getUsername(), admin.getPassword(), authorities);
    }
}
