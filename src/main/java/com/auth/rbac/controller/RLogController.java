package com.auth.rbac.controller;

import com.auth.rbac.dao.RLog;
import com.auth.rbac.dao.ResponseData;
import com.auth.rbac.service.RLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/api/v1/log")
public class RLogController {

    @Autowired
    private RLogService rLogService;

    @GetMapping(value = {"/",""})
    public String log(){
        return "log/audit";
    }

    @GetMapping(value = "/getPageRlog")
    @ResponseBody
    public ResponseData getRLog(@RequestParam(value = "page") int page,
                                @RequestParam(value = "limit") int limit){
        Page<RLog> rLogs = rLogService.getRlogByPage(page-1, limit);
        return new ResponseData<>(0, "succeed", rLogs.getTotalElements(), rLogs.getContent());
    }

}
