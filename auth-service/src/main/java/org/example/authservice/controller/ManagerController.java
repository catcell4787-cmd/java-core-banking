package org.example.authservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.authservice.enums.AccountStatus;
import org.example.authservice.model.dto.AccountDto;
import org.example.authservice.model.entity.Role;
import org.example.authservice.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/managers")
public class ManagerController {
    private final AccountService accountService;

    @GetMapping("/list")
    public List<AccountDto> listManagers() {
        Role role = new Role();
        role.setRole("MANAGER");
        return accountService.findByRole(List.of(role));
    }

    @GetMapping("/{email}")
    public ResponseEntity<?> getManager(@PathVariable String email) {
        return ResponseEntity.ok(accountService.findByEmail(email));
    }

    @PostMapping("/hire")
    public ResponseEntity<?> hireManager(@RequestBody AccountDto accountDto) {
        return ResponseEntity.ok(
                accountService.register(accountDto, "MANAGER", AccountStatus.ACTIVE));
    }

    @PatchMapping("/{email}/updateStatus")
    public ResponseEntity<?> updateStatus(@PathVariable String email,  @RequestBody AccountDto accountDto) {
        return ResponseEntity.ok(
                accountService.updateStatus(email, accountDto));
    }
}
