package com.auth.rbac.repository;

import com.auth.rbac.dao.Role;
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
public interface RoleRepository extends PagingAndSortingRepository<Role, Integer>,
        JpaSpecificationExecutor<Role> {

    @Transactional
    @Modifying
    @Query(value = "delete from role where id in (:idList)", nativeQuery = true)
    void deleteRoles(@Param(value = "idList") List<Integer> idList);

    Optional<Role> findRoleByName(@NotNull String name);

    @Query(value = "select id as id, name as name from role", nativeQuery = true)
    List<Map<String, Object>> findAllName();
}
