package com.auth.rbac.repository;

import com.auth.rbac.dao.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    User findUserByUsername(@NotNull String username);
}
