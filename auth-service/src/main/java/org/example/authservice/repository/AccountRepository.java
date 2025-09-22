package org.example.authservice.repository;

import org.example.authservice.model.entity.Account;
import org.example.authservice.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByEmail(String email);

    boolean existsByEmail(String email);

    List<Account> findByRoleEquals(List<Role> role);
}
