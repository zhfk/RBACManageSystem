package com.auth.rbac.repository;

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
public interface ResourceRepository extends PagingAndSortingRepository<Resource, Integer>,
        JpaSpecificationExecutor<Resource> {

    @Transactional
    @Modifying
    @Query("delete from Resource where id in (:idList)")
    void deleteResources(@Param(value = "idList") List<Integer> idList);

    Optional<Resource> findResourceByName(@NotNull String name);

    @Query("select id as id, name as name from Resource ")
    List<Map<String, Object>> findAllName();

    @Query("select name as name from Resource ")
    List<String> findNames();
}
