package com.auth.rbac.repository;

import com.auth.rbac.dao.RosourceHasPrivilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ResourceBindPrivilegeRepository extends JpaRepository<RosourceHasPrivilege, Integer> {

    @Transactional
    @Modifying
    @Query(value = "insert into rosource_has_privilege (`resource`, `privilege`) values (:resource, :privilege)", nativeQuery = true)
    void insert(@Param(value = "resource") String resource, @Param(value = "privilege") String privilege);

    @Transactional
    @Modifying
    @Query(value = "delete from rosource_has_privilege where `resource`=:resource and  `privilege`=:privilege", nativeQuery = true)
    void delete(@Param(value = "resource") String resource, @Param(value = "privilege") String privilege);


    @Query(value = "select privilege from rosource_has_privilege where `resource`=:resource", nativeQuery = true)
    List<String> findAllPrivilege(@Param(value = "resource") String resource);
}
