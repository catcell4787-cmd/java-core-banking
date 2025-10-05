package org.bank.authservice.controller;

import lombok.RequiredArgsConstructor;
import org.bank.authservice.enums.Status;
import org.bank.authservice.enums.Role;
import org.bank.authservice.model.dto.AccountDto;
import org.bank.authservice.model.entity.Account;
import org.bank.authservice.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/clients")
public class ClientsController {

    private final AccountService accountService;

    @GetMapping("/list")
    public List<Account> getClients() {
        return accountService.findByRole(Role.CLIENT.toString());
    }

    @GetMapping("/{email}")
    public ResponseEntity<?> getClient(@PathVariable String email) {
        return ResponseEntity.ok(accountService.findByEmail(email));
    }

    @PostMapping("/addClient")
    public ResponseEntity<?> addClient(@RequestBody AccountDto accountDto) {
        return ResponseEntity.ok(
                accountService.register(accountDto, Role.CLIENT, Status.ACTIVE)
        );
    }

    @PatchMapping("/{email}/updateStatus")
    public ResponseEntity<?> updateStatus(@PathVariable String email,  @RequestBody AccountDto accountDto) {
        return ResponseEntity.ok(accountService.updateStatus(email, accountDto));
    }

    @PostMapping("/{email}/registerCard")
    public ResponseEntity<?> registerCard(@PathVariable String email, @RequestBody AccountDto accountDto) {
        return new ResponseEntity<>(accountService.registerCard(email, accountDto), HttpStatus.OK);
    }
}
