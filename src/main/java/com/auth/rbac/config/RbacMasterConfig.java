package com.auth.rbac.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Data
@ConfigurationProperties(prefix = "rbacmaster")
public class RbacMasterConfig {

    private String initAdminPassword;

    public String getInitBCryptPasswordEncoderPwd(){
        return new BCryptPasswordEncoder().encode(initAdminPassword);
    }

    public String getInitAdminRole(){
        return "ADMIN";
    }

}
