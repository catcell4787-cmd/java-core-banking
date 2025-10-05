package org.example.authservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.authservice.enums.AccountStatus;
import org.example.authservice.model.dto.AccountDto;
import org.example.authservice.repository.RedisRoleRepository;
import org.example.authservice.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/clients")
public class ClientsController {

    private final AccountService accountService;
    private final RedisRoleRepository redisRoleRepository;

    @GetMapping("/list")
    public List<AccountDto> getClients() {
//        Role role = new Role();
//        role.setRole("CLIENT");
//        return accountService.findByRole(Set.of(role));
        return new ArrayList<>();
    }

    @GetMapping("/{email}")
    public ResponseEntity<?> getClient(@PathVariable String email) {
        return ResponseEntity.ok(accountService.findByEmail(email));
    }

    @PostMapping("/addClient")
    public ResponseEntity<?> addClient(@RequestBody AccountDto accountDto) {
        return ResponseEntity.ok(
                accountService.register(accountDto, "CLIENT", AccountStatus.ACTIVE)
        );
    }

    @PatchMapping("/{email}/updateStatus")
    public ResponseEntity<?> updateStatus(@PathVariable String email,  @RequestBody AccountDto accountDto) {
        return ResponseEntity.ok(accountService.updateStatus(email, accountDto));
    }
}
