package com.auth.rbac.service.Impl;

import com.auth.rbac.dao.RLog;
import com.auth.rbac.repository.RlogRepository;
import com.auth.rbac.service.RLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class RlogServiceImpl implements RLogService {

    @Autowired
    private RlogRepository rlogRepository;

    @Override
    public void save(RLog rLog) {
        rlogRepository.save(rLog);
    }

    @Override
    public Page<RLog> getRlogByPage(int page, int limit) {
        return rlogRepository.findAll(PageRequest.of(page, limit, new Sort(Sort.Direction.DESC,"timestamp")));
    }
}
