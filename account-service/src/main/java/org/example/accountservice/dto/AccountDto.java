package org.example.accountservice.dto;
import lombok.Data;
import org.example.accountservice.role.AccountRole;
import org.example.accountservice.role.AccountStatus;

@Data
public class AccountDto {
    private String username;
    private String email;
    private AccountRole accountRole;
    private AccountStatus accountStatus;
}
