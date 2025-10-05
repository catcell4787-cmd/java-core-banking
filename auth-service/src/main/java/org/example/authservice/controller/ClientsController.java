package org.example.authservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.authservice.enums.AccountStatus;
import org.example.authservice.enums.Role;
import org.example.authservice.model.dto.AccountDto;
import org.example.authservice.model.entity.Account;
import org.example.authservice.service.AccountService;
import org.example.authservice.service.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/clients")
public class ClientsController {

    private final AccountService accountService;
    private final RoleService roleService;

    @GetMapping("/list")
    public List<Account> getClients() {
        return roleService.findByRole(Role.CLIENT.toString());
    }

    @GetMapping("/{email}")
    public ResponseEntity<?> getClient(@PathVariable String email) {
        return ResponseEntity.ok(accountService.findByEmail(email));
    }

    @PostMapping("/addClient")
    public ResponseEntity<?> addClient(@RequestBody AccountDto accountDto) {
        return ResponseEntity.ok(
                accountService.register(accountDto, Role.CLIENT, AccountStatus.ACTIVE)
        );
    }

    @PatchMapping("/{email}/updateStatus")
    public ResponseEntity<?> updateStatus(@PathVariable String email,  @RequestBody AccountDto accountDto) {
        return ResponseEntity.ok(accountService.updateStatus(email, accountDto));
    }
}
