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
    @Query("delete from Privilege where id in (:idList)")
    void deletePrivileges(@Param(value = "idList") List<Integer> idList);

    Optional<Privilege> findPrivilegeByName(@NotNull String name);

    @Query("select id as id, name as name from Privilege ")
    List<Map<String, Object>> findAllName();
}
