package com.auth.rbac.repository;

import com.auth.rbac.dao.Privilege;
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
    @Query(value = "delete from Privilege where id in (:idList)", nativeQuery = true)
    void deletePrivileges(@Param(value = "idList") List<Integer> idList);

    @Query(value = "select id as id, `name` as `name`, resource as resource, `desc` as `desc` from Privilege where resource=:resource and `name`=:name ", nativeQuery = true)
    Optional<Privilege> findPrivilegeByNameAndResource(@Param(value = "resource") String resource, @Param(value = "name") String name);

    @Query(value = "select id as id, name as name from Privilege where resource=:resource", nativeQuery = true)
    List<Map<String, Object>> findAllName(@Param(value = "resource") String resource);

    @Query(value = "select id as id, name as name from Privilege ", nativeQuery = true)
    List<Map<String, Object>> findAllName();
}
