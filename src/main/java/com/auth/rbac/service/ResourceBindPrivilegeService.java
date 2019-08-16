package com.auth.rbac.service;

import java.util.List;

public interface ResourceBindPrivilegeService {
    void bind(String resource, String privilege);
    void unbind(String resource, String privilege);
    List<String> getBindedPrivileges(String resource);
}
