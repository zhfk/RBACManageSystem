package com.auth.rbac.service;

import java.util.List;

public interface GrantService {
    List<List<String>> getPermission(String user, String resource);
    List<String> getRole(String user);
}
