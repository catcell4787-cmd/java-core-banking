package org.example.accountservice.dto;

import lombok.Data;
import org.example.accountservice.role.AccountRole;

@Data
public class AccountDto {
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private AccountRole accountRole;
}
