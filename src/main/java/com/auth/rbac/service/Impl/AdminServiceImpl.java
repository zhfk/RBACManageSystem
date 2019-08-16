package com.auth.rbac.service.Impl;

import com.auth.rbac.config.RbacMasterConfig;
import com.auth.rbac.dao.Admin;
import com.auth.rbac.dao.RLog;
import com.auth.rbac.repository.AdminRepository;
import com.auth.rbac.service.AdminService;
import com.auth.rbac.service.RLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private RbacMasterConfig rbacConfig;

    @Autowired
    private RLogService rLogService;

    @Override
    public Boolean update(Admin admin) {
        Optional<Admin> optionalAdmin = adminRepository.findById(1L);
        Admin opAdmin = optionalAdmin.get();
        if(!StringUtils.isEmpty(admin.getPassword())){
            opAdmin.setPassword(new BCryptPasswordEncoder().encode(admin.getPassword()));
        }
        opAdmin.setAphorism(admin.getAphorism());
        opAdmin.setBirthday(admin.getBirthday());
        opAdmin.setForSpareTime(admin.getForSpareTime());
        opAdmin.setGender(admin.getGender());
        opAdmin.setHobby(admin.getHobby());
        opAdmin.setNativePlace(admin.getNativePlace());
        opAdmin.setPersonalDescription(admin.getPersonalDescription());
        adminRepository.save(opAdmin);
        return true;
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
            admin.setPassword(rbacConfig.getInitBCryptPasswordEncoderPwd());
            admin.setRoles(rbacConfig.getInitAdminRole());
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
            if(adminRepository.count() == 0){
                Admin admin = new Admin();
                admin.setId(1L);
                admin.setUsername("admin");
                admin.setPassword(rbacConfig.getInitBCryptPasswordEncoderPwd());
                admin.setRoles(rbacConfig.getInitAdminRole());
                adminRepository.save(admin);
                opAdmin = adminRepository.findByUsername(username);
            }else{
                throw new UsernameNotFoundException("用户名不存在");
            }
        }
        Admin admin = opAdmin.get();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        Arrays.stream(admin.getRoles().split(","))
                .allMatch(role -> authorities.add(new SimpleGrantedAuthority(role)));
        rLogService.save(new RLog(username, "登陆系统",new Timestamp(System.currentTimeMillis()), ""));
        return new User(admin.getUsername(), admin.getPassword(), authorities);
    }
}
