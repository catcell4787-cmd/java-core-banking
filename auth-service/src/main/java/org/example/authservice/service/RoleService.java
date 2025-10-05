package org.example.authservice.service;

import org.example.authservice.enums.Role;
import org.example.authservice.model.entity.Account;

import java.util.List;

public interface RoleService {
    void saveRole(String userName, Role role);
    Object getRoleForUser(String username);
    List<Account> findByRole(String role);
    void deleteRole(String role);
}
