package org.example.authservice.utils;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.example.authservice.enums.AccountStatus;
import org.example.authservice.model.entity.Account;
import org.example.authservice.repository.AccountRepository;
import org.example.authservice.repository.RedisRoleRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AdminServiceOneAndOnly {

    @Value("${admin.email}")
    private String adminEmail;

    @Value("${admin.password}")
    private String adminPassword;

    private final AccountRepository accountRepository;

    private final PasswordEncoder passwordEncoder;

    private final RedisRoleRepository redisRoleRepository;

    @PostConstruct
    public void createAdmin() {
        if (!accountRepository.existsByEmail(adminEmail)) {
            Account account = new Account();
            account.setEmail(adminEmail);
            account.setPassword(passwordEncoder.encode(adminPassword));
            account.setStatus(AccountStatus.ACTIVE);
            Set<Object> roles = new HashSet<>();
            roles.add("ADMIN");
            redisRoleRepository.setRolesForUser(account.getEmail(), roles);
            accountRepository.save(account);
        }
    }
}
