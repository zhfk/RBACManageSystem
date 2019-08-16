package com.auth.rbac;

import com.auth.rbac.config.RbacMasterConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(RbacMasterConfig.class)
public class RMSApplication {

	public static void main(String[] args) {
		SpringApplication.run(RMSApplication.class, args);
	}

}
