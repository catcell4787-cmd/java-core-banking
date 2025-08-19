package org.example.accountservice.dto;

import lombok.Data;
import org.example.accountservice.role.AccountRole;
import org.example.accountservice.role.AccountStatus;

@Data
public class AccountDto {
    private String email;
    private String firstname;
    private String lastname;
    private String password;
    private AccountRole role;
    private AccountStatus status;
}
