package com.auth.rbac.repository;

import com.auth.rbac.dao.Privilege;
import com.auth.rbac.dao.Resource;
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
public interface PrivilegeRepository extends PagingAndSortingRepository<Privilege, Integer>,
        JpaSpecificationExecutor<Privilege> {

    @Transactional
    @Modifying
    @Query(value = "delete from privilege where id in (:idList)", nativeQuery = true)
    void deletePrivileges(@Param(value = "idList") List<Integer> idList);

    @Query(value = "select id as id, name as name from privilege where resource=:resource", nativeQuery = true)
    List<Map<String, Object>> findAllName(@Param(value = "resource") String resource);

    @Query(value = "select id as id, name as name from privilege ", nativeQuery = true)
    List<Map<String, Object>> findAllName();

    Optional<Privilege> findPrivilegeByName(@NotNull String name);

}
