package com.auth.rbac.repository;

import com.auth.rbac.dao.Admin;
import com.auth.rbac.dao.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

    Optional<Admin> findByUsername(@NotNull String username);
}
