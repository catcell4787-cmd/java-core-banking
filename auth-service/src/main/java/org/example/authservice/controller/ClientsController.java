package org.example.authservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.authservice.model.entity.Account;
import org.example.authservice.service.AccountService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/clients")
public class ClientsController {

    private final AccountService accountService;

    @GetMapping("/list")
    public List<Account> getClients() {
        return accountService.findAll();
    }
}
