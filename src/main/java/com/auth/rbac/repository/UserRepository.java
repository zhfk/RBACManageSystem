package com.auth.rbac.repository;

import com.auth.rbac.dao.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Integer>,
        JpaSpecificationExecutor<User> {

    @Transactional
    @Modifying
    @Query(value = "delete from user where id in (:idList)", nativeQuery = true)
    void deleteUsers(@Param(value = "idList") List<Integer> idList);

    Optional<User> findUserByUsername(@NotNull String username);

    @Query(value = "select id as id, username as name from user", nativeQuery = true)
    List<Map<String, Object>> findAllUsername();
}
