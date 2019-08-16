package com.auth.rbac.service;


import com.auth.rbac.dao.RLog;
import org.springframework.data.domain.Page;

public interface RLogService {

    void save(RLog rLog);

    Page<RLog> getRlogByPage(int page, int limit);
}
