package org.example.authservice.utils;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.example.authservice.enums.Status;
import org.example.authservice.enums.Role;
import org.example.authservice.model.entity.Account;
import org.example.authservice.repository.AccountRepository;
import org.example.authservice.service.RoleService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminServiceOneAndOnly {

    @Value("${admin.email}")
    private String adminEmail;

    @Value("${admin.password}")
    private String adminPassword;

    private final AccountRepository accountRepository;

    private final PasswordEncoder passwordEncoder;

    private final RoleService roleService;

    @PostConstruct
    public void createAdmin() {
        if (!accountRepository.existsByEmail(adminEmail)) {
            Account account = new Account();
            account.setEmail(adminEmail);
            account.setPassword(passwordEncoder.encode(adminPassword));
            account.setStatus(Status.ACTIVE);
            roleService.saveRole(account.getEmail(), Role.ADMIN);
            accountRepository.save(account);
        }
    }
}
