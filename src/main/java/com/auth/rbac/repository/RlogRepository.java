package com.auth.rbac.repository;

import com.auth.rbac.dao.RLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RlogRepository extends PagingAndSortingRepository<RLog, Long>,
        JpaSpecificationExecutor<RLog> {
}
