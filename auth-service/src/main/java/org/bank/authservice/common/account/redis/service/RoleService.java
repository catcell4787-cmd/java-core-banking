package org.bank.authservice.common.account.redis.service;

import org.bank.authservice.enums.Role;
import org.bank.authservice.common.account.entity.Account;

import java.util.List;

public interface RoleService {
    void saveRole(String userName, Role role);
    String getRoleForUser(String username);
    List<Account> findByRole(String role);
    void deleteRole(String role);
}
